package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ComandoLog implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
				Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("ajdcheck")) {
			if(!p.hasPermission("koj.staff")) {
				
				p.sendMessage("§cVoc§ n§o tem permissao");
				/* caso  o jogador n§o tenha permiss§o  */
				
				return true;
			}
			  if(args[0].equalsIgnoreCase("reset")) {
				  ComandoResponder.log.set("Player", null);
				  
				  p.sendMessage("§cTodas as logs foram resetadas!");
			  }
		if(args.length == 0) {
			
			p.sendMessage("§7§l[KingoNetwork] >>§7 Digite /ajdcheck <ajudante>");
			return true;
			
			
		}
		
		if(args.length > 0) {
			Player pl = Bukkit.getPlayer(args[0]);
			if(pl == null) {
				return true;
			}
			String nome = pl.getName();
			
			ConfigurationSection cs = ComandoResponder.log.getConfig().getConfigurationSection("Player."+nome);
	
			   for(String ajd : cs.getKeys(true)){
			       String str = cs.getString(ajd);
//			       p.sendMessage(ajd+ ": " + str);
					p.sendMessage(" ");
					p.sendMessage(str.replace("&", "§").replace("$es", "\n"));
					p.sendMessage(" ");

		}
			   
			   
	}		   
		
			   
			   
			   
			   
			   
			   
		
		
	}
		return false;

}
	}
