package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public class Comando implements CommandExecutor {
	private final HashMap<Player, Integer> cooldown = new HashMap<>();
	private final HashMap<Player, Integer> tasks = new HashMap<>();
	private final BukkitScheduler scheduler = Bukkit.getScheduler();
	public static final T_Config reports = new T_Config(Main.getPlugin(Main.class), "duvidas.yml");
	private final T_Config ajudante = ComandoResponder.getAjudanteConfig();

	/**
	 * Combina argumentos em uma única mensagem.
	 */
	private String construirMensagem(String[] args, int startIndex) {
		StringBuilder sb = new StringBuilder();
		for (int i = startIndex; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		}
		return sb.toString().trim();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("§a§l[KingoNetwork] §7 Apenas jogadores podem usar este comando!");
			return true;
		}
		Player player = (Player) sender;

		switch (cmd.getName().toLowerCase()) {
			case "duvida":
				handleDuvidaCommand(player, args);
				break;

			case "perguntas":
				handlePerguntasCommand(player);
				break;

			case "ajudante":
				handleAjudanteCommand(player, args);
				break;

			default:
				return false;
		}

		return true;
	}

	/**
	 * Lógica para o comando "/duvida".
	 */
	private void handleDuvidaCommand(Player player, String[] args) {
		if (player.hasPermission("koj.ajd")) {
			player.sendMessage("§7§l[KingoNetwork] >>§c§l Apenas jogadores podem usar este comando!");
			return;
		}

		if (args.length == 0) {
			player.sendMessage("§7§l[KingoNetwork] >>§7 Digite /duvida (sua dúvida)");
			player.sendMessage("§7§l[KingoNetwork] >>§7 Exemplo:§a /duvida 'Qual o nível máximo?'");
			return;
		}

		String reporter = player.getName().toLowerCase();
		if (reports.getConfig().contains("Player." + reporter)) {
			player.sendMessage("§7§l[KingoNetwork] >>§c§l Você já reportou, aguarde uma resposta!");
			return;
		}

		String mensagem = construirMensagem(args, 0);

		// Enviar mensagem aos ajudantes e operadores
		for (Player ajudante : Bukkit.getOnlinePlayers()) {
			if (ajudante.hasPermission("koj.ajd") || ajudante.isOp()) {
				ajudante.sendMessage("§7§l[KingoNetwork] >>§7 O player:§a " + reporter);
				ajudante.sendMessage("§7§l[§6§lDu§e§lvi§6§lda§7§l] >>§7 Enviou uma dúvida:§a " + mensagem);
				ajudante.sendMessage("§7§l[§6§lDu§e§lvi§6§lda§7§l] >>§c Digite /responder " + reporter + " resposta");
			}
		}

		// Logar a dúvida no console
		Bukkit.getConsoleSender().sendMessage("§a§l[AJD CONSOLE] §7§l O player " + reporter + " reportou: §a§l" + mensagem);

		// Enviar confirmação ao jogador
		player.sendMessage(" ");
		player.sendMessage("§7§l[KingoNetwork] >>§7 Sua dúvida foi publicada!");
		player.sendMessage("§7§l[KingoNetwork] >>§7 Sua dúvida: §a" + mensagem);

		// Salvar no arquivo de configuração
		reports.set("Player." + reporter, mensagem);
		reports.saveConfig();
	}

	/**
	 * Lógica para o comando "/perguntas".
	 */
	private void handlePerguntasCommand(Player player) {
		if (!player.hasPermission("koj.ajd")) {
			player.sendMessage("§7§l[KingoNetwork] >>§7 Você não tem permissão!");
			return;
		}

		ConfigurationSection playerReports = reports.getConfig().getConfigurationSection("Player");
		if (playerReports == null) {
			player.sendMessage("§cSem dúvidas no momento.");
			return;
		}

		player.sendMessage("§a§l≪≪§2 D U V I D A S §a§l≫≫");
		for (String reporter : playerReports.getKeys(false)) {
			String duvida = playerReports.getString(reporter);
			player.sendMessage("§7§l[KingoNetwork] §7>> Player: §a" + reporter);
			player.sendMessage("§7§l[KingoNetwork] §7>> Dúvida: §a" + duvida);
			player.sendMessage(" ");
		}
	}

	/**
	 * Lógica para o comando "/ajudante".
	 */
	private void handleAjudanteCommand(Player player, String[] args) {
		if (!player.hasPermission("koj.ajd")) {
			player.sendMessage("§7§l[KingoNetwork] >>§7 Você não tem permissão!");
			return;
		}

		if (args.length == 0) {
			showAjudantesInfo(player);
		} else {
			switch (args[0].toLowerCase()) {
				case "pontos":
					showPlayerPoints(player);
					break;
				case "anuncio":
					announceHelp(player);
					break;
				case "help":
					showAjudanteHelp(player);
					break;
				default:
					player.sendMessage("§7§l[KingoNetwork] >>§7 Comando desconhecido!");
			}
		}
	}

	/**
	 * Mostra informações de ajudantes.
	 */
	private void showAjudantesInfo(Player player) {
		ConfigurationSection ajudantesSection = ajudante.getConfig().getConfigurationSection("Ajudante");
		if (ajudantesSection == null) {
			player.sendMessage("§cNenhum ajudante registrado.");
			return;
		}

		player.sendMessage("§a§l≪≪§2 Ajudantes §a§l≫≫");
		for (String ajudante : ajudantesSection.getKeys(false)) {
			int pontos = ajudantesSection.getInt(ajudante);
			player.sendMessage("§7§l[KingoNetwork] §7>> Ajudante: §a" + ajudante);
			player.sendMessage("§7§l[KingoNetwork] §7>> Dúvidas respondidas: §a" + pontos);
			player.sendMessage(" ");
		}
	}

	/**
	 * Mostra os pontos do jogador.
	 */
	private void showPlayerPoints(Player player) {
		String pontos = ComandoPonto.pts.getConfig().getString("Pontos." + player.getName());
		player.sendMessage("§7§l[KingoNetwork] §7>> Atualmente você tem §a" + pontos + " §7pontos.");
	}

	/**
	 * Faz o anúncio de ajuda.
	 */
	private void announceHelp(Player player) {
		if (tasks.containsKey(player)) {
			player.sendMessage("§7§l[KingoNetwork] §7>> Aguarde para anunciar novamente!");
			return;
		}

		int taskID = scheduler.scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			int cooldown = 120;

			@Override
			public void run() {
				if (cooldown > 0) {
					cooldown--;
					Comando.this.cooldown.put(player, cooldown);
				} else {
					scheduler.cancelTask(tasks.get(player));
					Comando.this.cooldown.remove(player);
					tasks.remove(player);
				}
			}
		}, 0, 20);

		tasks.put(player, taskID);
		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage("§7§l[KingoNetwork] >>§7 O Ajudante §a" + player.getName() + " §7está respondendo dúvidas.");
		Bukkit.broadcastMessage("§7§l[KingoNetwork] >>§7 Digite §a/duvida (sua dúvida)");
		Bukkit.broadcastMessage("§7§l[KingoNetwork] >>§7 Exemplo: §a/duvida 'Qual o nível máximo?'");
		Bukkit.broadcastMessage(" ");
	}

	/**
	 * Mostra a ajuda do comando "/ajudante".
	 */
	private void showAjudanteHelp(Player player) {
		player.sendMessage("§7§l[KingoNetwork] >>§7 Digite /ajudante anuncio (para anunciar que está respondendo dúvidas)");
		player.sendMessage("§7§l[KingoNetwork] >>§7 Digite /perguntas (para ver a lista de dúvidas)");
		player.sendMessage("§7§l[KingoNetwork] >>§7 Digite /responder <player> <resposta> (para responder)");
		player.sendMessage("§7§l[KingoNetwork] >>§7 Digite /ajudante pag (para pegar seu pagamento)");
		player.sendMessage("§7§l[KingoNetwork] >>§7 Digite /ajudante pontos (para ver sua quantidade de pontos)");
	}
}
