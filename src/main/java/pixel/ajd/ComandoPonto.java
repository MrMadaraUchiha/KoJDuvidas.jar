package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ComandoPonto implements CommandExecutor{
	public static T_Config pts = new T_Config(Main.getPlugin(Main.class), "pts.yml");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
			Player p = (Player)sender; /* player que digitou o comando*/
			
		if(cmd.getName().equalsIgnoreCase("darponto")) {
			if(!p.hasPermission("koj.staff")) {
 			   p.sendMessage("§7§l[KingoNetwork] >>§7 Voc§ n§o tem permiss§o !");
				return true;
			}
			if(args.length == 0) {
				p.sendMessage("§7§l[KingoNetwork] >>§7 Digite /darponto <ajudante>");
				p.sendMessage("§7§l[KingoNetwork] >>§7 Exemplo: /darponto Jabu");

				return true;
			}
			
		if(args.length == 1) {
			Player pl = Bukkit.getPlayer(args[1]); /* var que diz q o args § player */
			if(!pl.hasPermission("koj.ajd")) {
				p.sendMessage("§§7§l[KingoNetwork] >>§7 O player n§o § um ajudante! ");
				return true;
			}
			int pontos = pts.getConfig().getInt("Pontos."+pl.getName());
		   	int total = pontos +1 ;
		   if(pts.getConfig().getString("Pontos."+pl.getName()) == null){
			   /* se n§o tiver o player na config ajudante, seta 1 */
				p.sendMessage("§7§l[KingoNetwork] >>§7 Voc§ deu §1§7 ponto para §a"+pl.getName());

				pts.set("Pontos."+pl.getName(), 1);
				pts.saveConfig();
				return true;
		   }
		   
		   
		   
		   
		   
		   if(pts.getConfig().contains("Pontos."+pl.getName())){
				p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Voc§ deu §1§7 ponto para §a"+pl.getName());

			   /*se tiver o player na config ajudante pega o numero de pontos q ele tem e soma com +1*/
    		   pts.set("Pontos."+pl.getName(), total);
    		   pts.saveConfig();
			   
		   }
			
		}
		if(args.length == 2) {
			Player pls = Bukkit.getPlayer(args[1]);
			 Integer arg = Integer.valueOf(args[2]);
			 int pontos = pts.getConfig().getInt("Pontos."+pls.getName());
			   	int total = pontos +arg ;
			 if(!pls.hasPermission("koj.ajd")) {
					p.sendMessage("§§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 O player n§o § um ajudante! ");
					return true;
				}
			 if(pts.getConfig().getString("Pontos."+pls.getName()) == null){
				   /* se n§o tiver o player na config ajudante, seta 1 */
					p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Voc§ deu "+arg+" ponto para §a"+pls.getName());

					pts.set("Pontos."+pls.getName(), arg);
					pts.saveConfig();
					return true;
			   }
			 
			 if(pts.getConfig().contains("Pontos."+pls.getName())){
					p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Voc§ deu "+arg+" ponto para §a"+pls.getName());

				   /*se tiver o player na config ajudante pega o numero de pontos q ele tem e soma com +1*/
	    		   pts.set("Pontos."+pls.getName(), total);
	    		   pts.saveConfig();
				   
			   }
			 
			
			
			
		}
			
			
			
			
			
		}
		if(cmd.getName().equalsIgnoreCase("tirarponto")) {
			if(!p.hasPermission("koj.staff")) {
 			   p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Voc§ n§o tem permiss§o !");
				return true;
			}
			if(args.length == 0) {
				p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Digite /tirarponto <ajudante>");
				p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Exemplo: /tirarponto Jabu");

				return true;
			}
			
		if(args.length > 0) {
			Player pl = Bukkit.getPlayer(args[0]); /* var que diz q o args § player */

			int pontos = pts.getConfig().getInt("Pontos."+pl.getName());
		   	int total = --pontos ;
		   if(pts.getConfig().getInt("Pontos."+pl.getName()) == 0){
			   /* se n§o tiver o player na config ajudante, seta 0 */
				p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 O Ajudante n§o possui pontos!");

				pts.set("Pontos."+pl.getName(), 0);
				pts.saveConfig();
				return true;
		   }
		   if(pts.getConfig().contains("Pontos."+pl.getName())){
				p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 Voc§ tirou §1§7 ponto para §a"+pl.getName());

			   /*se tiver o player na config ajudante pega o numero de pontos q ele tem e soma com +1*/
   		   pts.set("Pontos."+pl.getName(), total);
   		   pts.saveConfig();
			   
		   }
			
		}

	if(cmd.getName().equalsIgnoreCase("verpontos")) {
		if(!p.hasPermission("koj.staff")) {
			p.sendMessage("§c§l[§6§lK§e§lo§6§lM§c§l] >>§c Voc§ n§o tem permiss§o");

		}
		if(args.length == 0) {
			return true;
		}
		if(args.length > 0) {
		Player pls = Bukkit.getPlayer(args[0]);
		if(pls == null) {
			p.sendMessage("§c§l[§6§lK§e§lo§6§lM§c§l] >>§c Jogador n§o encontrado!");

			return true;
		}
		String nome = pls.getName();
		int pontitos = pts.getConfig().getInt("Pontos."+nome);
		p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 O Ajudante §a"+nome+" §7possui§e "+pontitos+" §7Pontos!");
		
		
		}
		
		
		
	}
		
	}
return false;
}}
