package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ComandoLog implements CommandExecutor {

	private final T_Config log = ComandoResponder.getLogConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando só pode ser usado por jogadores.");
			return true;
		}

		Player player = (Player) sender;

		if (!cmd.getName().equalsIgnoreCase("ajdcheck")) {
			return false;
		}

		if (!player.hasPermission("koj.staff")) {
			player.sendMessage("§cVocê não tem permissão para usar este comando.");
			return true;
		}

		if (args.length == 0) {
			mostrarAjuda(player);
			return true;
		}

		if (args[0].equalsIgnoreCase("reset")) {
			if (args.length == 1) {
				resetarTodasLogs(player);
				return true;
			}

			resetarLogsAjudante(player, args[1]);
			return true;
		}

		verificarLogs(player, args[0]);
		return true;
	}

	/**
	 * Mostra a mensagem de ajuda para o comando /ajdcheck.
	 */
	private void mostrarAjuda(Player player) {
		player.sendMessage("§7§l[KingoNetwork] >> §7Uso do comando:");
		player.sendMessage("§a/ajdcheck <ajudante> §7- Verificar logs de um ajudante.");
		player.sendMessage("§a/ajdcheck reset §7- Resetar todas as logs.");
		player.sendMessage("§a/ajdcheck reset <ajudante> §7- Resetar as logs de um ajudante específico.");
	}

	/**
	 * Reseta todas as logs registradas no sistema.
	 */
	private void resetarTodasLogs(Player player) {
		log.set("Player", null);
		log.saveConfig();
		player.sendMessage("§c§l[KingoNetwork] >> Todas as logs foram resetadas!");
	}

	/**
	 * Reseta as logs de um ajudante específico.
	 *
	 * @param player   Quem está executando o comando.
	 * @param ajudante Nome do ajudante cujas logs serão resetadas.
	 */
	private void resetarLogsAjudante(Player player, String ajudante) {
		Player ajudantePlayer = Bukkit.getPlayer(ajudante);

		if (ajudantePlayer == null) {
			player.sendMessage("§cJogador não encontrado ou offline: " + ajudante);
			return;
		}

		String nome = ajudantePlayer.getName();
		ConfigurationSection ajudanteSection = log.getConfig().getConfigurationSection("Player." + nome);

		if (ajudanteSection == null) {
			player.sendMessage("§7§l[KingoNetwork] >> §cNenhuma log encontrada para o ajudante: " + nome);
			return;
		}

		log.set("Player." + nome, null);
		log.saveConfig();
		player.sendMessage("§7§l[KingoNetwork] >> §aLogs do ajudante §6" + nome + "§a foram resetadas com sucesso.");
	}

	/**
	 * Verifica e exibe os logs de um ajudante específico.
	 *
	 * @param player   Quem está executando o comando.
	 * @param ajudante Nome do ajudante a ser verificado.
	 */
	private void verificarLogs(Player player, String ajudante) {
		Player ajudantePlayer = Bukkit.getPlayer(ajudante);

		if (ajudantePlayer == null) {
			player.sendMessage("§cJogador não encontrado: " + ajudante);
			return;
		}

		String nome = ajudantePlayer.getName();
		ConfigurationSection cs = log.getConfig().getConfigurationSection("Player." + nome);

		if (cs == null) {
			player.sendMessage("§7§l[KingoNetwork] >> §cNenhuma log encontrada para o ajudante: " + nome);
			return;
		}

		player.sendMessage("§7§l[KingoNetwork] >> §aLogs para o ajudante: §6" + nome);

		for (String chave : cs.getKeys(true)) {
			String logMensagem = cs.getString(chave);
			if (logMensagem != null) {
				player.sendMessage(" ");
				player.sendMessage(logMensagem.replace("&", "§").replace("$es", "\n"));
				player.sendMessage(" ");
			}
		}
	}
}
