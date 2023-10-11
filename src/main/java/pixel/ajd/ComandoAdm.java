package pixel.ajd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoAdm implements CommandExecutor{
	


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("ajd")) {
				if(!(sender instanceof Player)) {
					
					return true;
				}
				
				if(p.hasPermission("koj.staff")) {
					if(args.length == 0) {
						p.sendMessage(" ");
	    			p.sendMessage("§c§l[KingoNetwork] >>§c /ajd zerarajd (zerar rank de ajd)");
	    			p.sendMessage("§c§l[KingoNetwork] >>§c /ajd zerarrpt (zerar perguntas)");
	    			p.sendMessage("§c§l[KingoNetwork] >>§c /darponto <player> (dar 1 ponto ao ajudante)");
	    			p.sendMessage("§c§l[KingoNetwork] >>§c /verpontos (para ver seus pontos)");
	    			p.sendMessage("§c§l[KingoNetwork] >>§c /tirarponto <ajudante> ");

	    			return true;
					}
					
					if(args[0].equalsIgnoreCase("zerarajd")) {
						ComandoResponder.ajudante.set("Ajudante", null);
		    			p.sendMessage("§c§l[§6§lK§e§lo§6§lM§c§l] >>§c Rank de ajudantes zerado");

					}
					if(args[0].equalsIgnoreCase("zerarrpt")) {
						Comando.reports.set("Player", null);
		    			p.sendMessage("§c§l[§6§lK§e§lo§6§lM§c§l] >>§c Perguntas zeradas");

					}
					
					
				}
	
			}
			

		
		
		
		
		return false;
	}

}
