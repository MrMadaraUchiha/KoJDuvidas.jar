package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoPonto implements CommandExecutor {
	public static T_Config pts = new T_Config(Main.getPlugin(Main.class), "pts.yml");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando só pode ser usado por jogadores.");
			return true;
		}

		Player p = (Player) sender;

		switch (cmd.getName().toLowerCase()) {
			case "darponto":
				return darPonto(p, args);
			case "tirarponto":
				return tirarPonto(p, args);
			case "verpontos":
				return verPontos(p, args);
			default:
				return false;
		}
	}

	private boolean darPonto(Player p, String[] args) {
		if (!p.hasPermission("koj.staff")) {
			p.sendMessage("§7§l[KingoNetwork] >> §7Você não tem permissão!");
			return true;
		}

		if (args.length == 0) {
			p.sendMessage("§7§l[KingoNetwork] >> §7Digite /darponto <ajudante>");
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null || !target.hasPermission("koj.ajd")) {
			p.sendMessage("§7§l[KingoNetwork] >> §7O jogador não é um ajudante válido!");
			return true;
		}

		int pontos = pts.getConfig().getInt("Pontos." + target.getName(), 0);

		// Verifica se um segundo argumento foi passado para a quantidade de pontos
		int total = pontos;

		if (args.length == 2) {
			try {
				int pontosAAdicionar = Integer.parseInt(args[1]);
				total = pontos + pontosAAdicionar;
			} catch (NumberFormatException e) {
				p.sendMessage("§7§l[KingoNetwork] >> §7O segundo argumento deve ser um número.");
				return true;
			}
		} else {
			total = pontos + 1; // Se não passar a quantidade, adiciona 1 ponto por padrão
		}

		pts.set("Pontos." + target.getName(), total);
		pts.saveConfig();

		p.sendMessage("§7§l[KingoNetwork] >> §7Você deu §1" + (total - pontos) + " ponto(s) para §a" + target.getName());
		return true;
	}

	private boolean tirarPonto(Player p, String[] args) {
		if (!p.hasPermission("koj.staff")) {
			p.sendMessage("§7§l[KingoNetwork] >> §7Você não tem permissão!");
			return true;
		}

		if (args.length == 0) {
			p.sendMessage("§7§l[KingoNetwork] >> §7Digite /tirarponto <ajudante>");
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null || !target.hasPermission("koj.ajd")) {
			p.sendMessage("§7§l[KingoNetwork] >> §7O jogador não é um ajudante válido!");
			return true;
		}

		int pontos = pts.getConfig().getInt("Pontos." + target.getName(), 0);

		// Verifica se um segundo argumento foi passado para a quantidade de pontos
		int total = pontos;

		if (args.length == 2) {
			try {
				int pontosARemover = Integer.parseInt(args[1]);
				total = pontos - pontosARemover;
			} catch (NumberFormatException e) {
				p.sendMessage("§7§l[KingoNetwork] >> §7O segundo argumento deve ser um número.");
				return true;
			}
		} else {
			total = pontos - 1; // Se não passar a quantidade, remove 1 ponto por padrão
		}

		// Se os pontos do jogador forem menores que 0, não permitir
		if (total < 0) {
			p.sendMessage("§7§l[KingoNetwork] >> §7O Ajudante não possui pontos suficientes!");
			return true;
		}

		pts.set("Pontos." + target.getName(), total);
		pts.saveConfig();

		p.sendMessage("§7§l[KingoNetwork] >> §7Você tirou §1" + (pontos - total) + " ponto(s) de §a" + target.getName());
		return true;
	}

	private boolean verPontos(Player p, String[] args) {
		if (!p.hasPermission("koj.staff")) {
			p.sendMessage("§c§l[KingoNetwork] >> §cVocê não tem permissão");
			return true;
		}

		if (args.length == 0) {
			p.sendMessage("§7§l[KingoNetwork] >> §7Digite /verpontos <ajudante>");
			return true;
		}

		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			p.sendMessage("§c§l[KingoNetwork] >> §cJogador não encontrado!");
			return true;
		}

		int pontos = pts.getConfig().getInt("Pontos." + target.getName(), 0);
		p.sendMessage("§7§l[KingoNetwork] >> §7O Ajudante §a" + target.getName() + " §7possui " + pontos + " ponto(s)");
		return true;
	}
}
