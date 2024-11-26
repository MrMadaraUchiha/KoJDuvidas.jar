package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoVPonto implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		// Verifica se quem executou o comando é um jogador
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando só pode ser usado por jogadores.");
			return true;
		}

		Player p = (Player) sender;

		// Comando de visualização de pontos
		if (cmd.getName().equalsIgnoreCase("verpontos")) {
			if (!p.hasPermission("koj.staff")) {
				p.sendMessage("§cVocê não tem permissão.");
				return true;
			}

			if (args.length == 0) {
				p.sendMessage("§7§l[KingoNetwork] >> §7Digite /verpontos <nome do ajudante>");
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage("§7§l[KingoNetwork] >> §7Ajudante não encontrado ou não está online.");
				return true;
			}

			// Verifica se o jogador é um ajudante válido
			if (!target.hasPermission("koj.ajd")) {
				p.sendMessage("§7§l[KingoNetwork] >> §7O jogador não é um ajudante.");
				return true;
			}

			// Verifica e exibe a quantidade de pontos
			String path = "Pontos." + target.getName();
			if (ComandoPonto.pts.getConfig().contains(path)) {
				int pontos = ComandoPonto.pts.getConfig().getInt(path);
				p.sendMessage("§7§l[KingoNetwork] >> §7O Ajudante §a" + target.getName() + " §7tem §e" + pontos + " §7ponto(s).");
			} else {
				p.sendMessage("§7§l[KingoNetwork] >> §7O Ajudante §a" + target.getName() + " §7não possui pontos.");
			}
		}

		return false;
	}
}
