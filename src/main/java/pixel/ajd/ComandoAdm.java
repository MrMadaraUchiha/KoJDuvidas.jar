package pixel.ajd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoAdm implements CommandExecutor {

	private final T_Config ajudante = ComandoResponder.getAjudanteConfig();
	private final T_Config reports = ComandoResponder.getReportsConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§c§l[KingoNetwork] >> Este comando só pode ser usado por jogadores.");
			return true;
		}

		Player player = (Player) sender;

		if (!cmd.getName().equalsIgnoreCase("ajd") || !player.hasPermission("koj.staff")) {
			player.sendMessage("§c§l[KingoNetwork] >> Você não tem permissão para usar este comando.");
			return true;
		}

		if (args.length == 0) {
			mostrarAjuda(player);
			return true;
		}

		switch (args[0].toLowerCase()) {
			case "zerarajd":
				zerarAjudantes(player);
				break;

			case "zerarrpt":
				zerarPerguntas(player);
				break;

			default:
				player.sendMessage("§c§l[KingoNetwork] >> Comando desconhecido. Use /ajd para ver os comandos disponíveis.");
				break;
		}

		return true;
	}

	/**
	 * Mostra os comandos disponíveis para administradores.
	 */
	private void mostrarAjuda(Player player) {
		player.sendMessage(" ");
		player.sendMessage("§c§l[KingoNetwork] >> §cComandos disponíveis:");
		player.sendMessage("§c /ajd zerarajd §7- Zerar o rank de ajudantes.");
		player.sendMessage("§c /ajd zerarrpt §7- Zerar todas as perguntas.");
		player.sendMessage("§c /darponto <player> §7- Dar 1 ponto ao ajudante.");
		player.sendMessage("§c /verpontos §7- Ver seus pontos.");
		player.sendMessage("§c /tirarponto <ajudante> §7- Remover 1 ponto do ajudante.");
	}

	/**
	 * Reseta o rank dos ajudantes.
	 */
	private void zerarAjudantes(Player player) {
		ajudante.set("Ajudante", null);
		ajudante.saveConfig();
		player.sendMessage("§c§l[KingoNetwork] >> §cO rank de ajudantes foi zerado.");
	}

	/**
	 * Reseta todas as perguntas registradas.
	 */
	private void zerarPerguntas(Player player) {
		reports.set("Player", null);
		reports.saveConfig();
		player.sendMessage("§c§l[KingoNetwork] >> §cTodas as perguntas foram zeradas.");
	}
}
