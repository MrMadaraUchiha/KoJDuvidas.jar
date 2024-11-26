package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pixel.utils.PointsFunctions;

public class ComandoResponder implements CommandExecutor {
	private static final T_Config log = new T_Config(Main.getPlugin(Main.class), "log.yml");
	private static final T_Config ajudante = new T_Config(Main.getPlugin(Main.class), "ajudante.yml");
	private static final T_Config reports = Comando.reports;

	public static T_Config getLogConfig() {
		return log;
	}
	public static T_Config getAjudanteConfig() {
		return ajudante;
	}
	public static T_Config getReportsConfig() {
		return reports;
	}

	private final PointsFunctions pointsFunctions = new PointsFunctions();

	/**
	 * Combina argumentos em uma única string.
	 */
	private String combinarMensagem(String[] args, int inicio) {
		StringBuilder sb = new StringBuilder();
		for (int i = inicio; i < args.length; i++) {
			sb.append(args[i]).append(" ");
		}
		return sb.toString().trim();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player p)) {
			sender.sendMessage("§cEste comando só pode ser usado por jogadores!");
			return true;
		}

		if (!cmd.getName().equalsIgnoreCase("responder")) return false;

		if (!p.hasPermission("koj.ajd")) {
			p.sendMessage("§7§l[KingoNetwork] >>§7 Você não tem permissão para usar este comando.");
			return true;
		}

		if (args.length < 2) {
			p.sendMessage("§7§l[KingoNetwork] >>§7 Use: /responder (jogador) (resposta)");
			p.sendMessage("§7§l[KingoNetwork] >>§7 Exemplo: /responder MrMadaraUchiha O nível máximo é 100");
			return true;
		}

		String reporterName = args[0].toLowerCase();
		Player reporter = Bukkit.getPlayer(reporterName);

		String mensagem = combinarMensagem(args, 1);

		if (reporter == null) {
			// Verificar se o jogador tem uma pergunta registrada
			if (!Comando.reports.getConfig().contains("Player." + reporterName.toLowerCase())) {
				p.sendMessage("§7§l[KingoNetwork] >>§7 O jogador §a" + reporterName.toLowerCase() +
						"§7 não tem nenhuma pergunta registrada.");
				return true;
			}

			// Jogador offline, salvar a resposta no arquivo
			Comando.reports.set("RespostasOffline." + reporterName.toLowerCase() + ".ajudante", p.getName());
			Comando.reports.set("RespostasOffline." + reporterName.toLowerCase() + ".mensagem", mensagem);
			Comando.reports.saveConfig();
			atualizarPontosAjudante(p.getName());
			// Avisar o ajudante que a resposta foi salva
			p.sendMessage("§7§l[KingoNetwork] >>§7 O jogador §a" + reporterName.toLowerCase() +
					"§7 está offline. A resposta foi salva e será enviada quando ele entrar.");

			return true;
		}

		String ajudanteName = p.getName();
		int totalReports = reports.getConfig().getInt("total", 0);
		String logPath = ajudanteName + "." + totalReports;

		if (reports.getConfig().getString("Player." + reporterName) != null) {
			responderDuvida(p, reporter, mensagem);
			salvarLog(p, reporter, mensagem, logPath);
			atualizarPontosAjudante(ajudanteName);
		} else {
			p.sendMessage("§7§l[KingoNetwork] >>§7 Não há dúvidas registradas para o jogador §a" + reporterName + "§7.");
		}

		return true;
	}

	/**
	 * Responde a dúvida do jogador e notifica outros jogadores com permissão.
	 */
	private void responderDuvida(Player ajudante, Player reporter, String mensagem) {
		reporter.sendMessage("§7§l[KingoNetwork] >>§7 O Ajudante: §a" + ajudante.getName());
		reporter.sendMessage("§7§l[KingoNetwork] >>§7 Respondeu: §a" + mensagem);
		reporter.sendMessage(" ");
		ajudante.sendMessage("§7§l[KingoNetwork] >>§7 Você respondeu o jogador §a" + reporter.getName());
		ajudante.sendMessage("§7§l[KingoNetwork] >>§7 Sua resposta foi: §a" + mensagem);

		reports.getConfig().set("Player." + reporter.getName().toLowerCase(), null);
		reports.saveConfig();

		for (Player staff : Bukkit.getOnlinePlayers()) {
			if (staff.hasPermission("koj.staff")) {
				staff.sendMessage("§c§l[KingoNetwork] >>§c O Ajudante §a" + ajudante.getName() +
						"§c respondeu §a" + reporter.getName());
				staff.sendMessage("§c§l[KingoNetwork] >>§c Resposta: §a" + mensagem);
			}
		}

		reporter.getWorld().playSound(reporter.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
	}

	/**
	 * Salva um log da resposta no arquivo de logs.
	 */
	private void salvarLog(Player ajudante, Player reporter, String mensagem, String logPath) {
		String logEntry = "&a O Ajudante &b" + ajudante.getName() +
				" &arespondeu o Jogador &b" + reporter.getName() +
				"&e com a resposta: &6" + mensagem;
		log.set("Player." + logPath, logEntry);
		log.saveConfig();
	}

	/**
	 * Atualiza os pontos do ajudante no arquivo de configuração.
	 */
	private void atualizarPontosAjudante(String ajudanteName) {
		int pontosAtuais = ajudante.getConfig().getInt("Ajudante." + ajudanteName, 0);
		ajudante.set("Ajudante." + ajudanteName, pontosAtuais + 1);
		ajudante.saveConfig();
		pointsFunctions.adicionarPontos(Bukkit.getPlayer(ajudanteName), 1);
	}
}
