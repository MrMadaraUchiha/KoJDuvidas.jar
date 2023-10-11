package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ComandoResponder implements CommandExecutor{
	public static T_Config log = new T_Config(Main.getPlugin(Main.class), "log.yml");
	public static T_Config ajudante = new T_Config(Main.getPlugin(Main.class), "ajudante.yml");
	
	
	public String Mensagem2(String[] args) {
		  StringBuilder sb = new StringBuilder();
	   for (int i = 1; i< args.length; i++) {
			   sb.append(args[i]);
			   sb.append(" ");}
	   return sb.toString();
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("responder")) {
			if(!p.hasPermission("koj.ajd")) {
				p.sendMessage("§7§l[KingoNetwork] >>§7 Voce n§o tem permissao");
				return true;
			}
			
			 if(args.length == 0 ) {
  			   p.sendMessage("§7§l[KingoNetwork] >>§7 Digite /responder (jogador) resposta");
  			   p.sendMessage("§7§l[KingoNetwork] >>§7 Exemplo: /responder MrMadaraUchiha O N§vel Maximo § 100");
  			   return true;
  		   }
			 
			 if(args.length > 0) {
				 String reporter = args[0];
  			   String ajd = p.getName();/*ajudante*/
  			   String mensagem = Mensagem2(args); /* toda a duvida*/
  			   Player players = Bukkit.getPlayer(reporter); /*player que reportou uma duvida*/
  	    	   String totals = ""+Comando.reports.getConfig().getInt("total"); /*numero atual de duvidas*/
  			   String as = ""+p.getName()+"."+totals; /* String de config*/


			   if(Comando.reports.getConfig().getString("Player."+reporter.toLowerCase()) != null) {
			players.sendMessage("§7§l[KingoNetwork] >>§7 O Ajudante : §a" + p.getName() );
			players.sendMessage("§7§l[KingoNetwork] >>§7 Respondeu : §a" + mensagem );
			players.sendMessage(" ");
			String logs = "&a O Ajudante &b"+p.getName()+" &arespondeu o Jogador &b"+players.getName()+"$es &aresposta:&6 "+mensagem+" !";
			   p.sendMessage(" ");
			   p.sendMessage("§7§l[KingoNetwork] >>§7 Voc§ respondeu o jogador §a" + reporter.toLowerCase());
			   p.sendMessage("§7§l[KingoNetwork] >>§7 Sua resposta foi §a" + mensagem);
			   Comando.reports.set("Player."+reporter.toLowerCase(), null);
			   Comando.reports.saveConfig();
			   for(Player plsa : Bukkit.getOnlinePlayers()) {
				  if(plsa.hasPermission("koj.staff")) {
				   plsa.sendMessage(" ");
				   plsa.sendMessage("§c§l[KingoNetwork] >>§c O Ajudante §a"+p.getName()+"§c respondeu §a"+reporter.toLowerCase());
				   plsa.sendMessage("§c§l[KingoNetwork] >>§c Resposta: §a"+mensagem);
			   }
			  }
			   log.set("Player."+as, logs);
			   log.saveConfig();
				  players.getWorld().playSound(players.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);


				   
				   
				   
				   
				   /* Abaixo entre ifs s§o o sistema de contagem de duvidas respondidas por cada ajudante*/
			   int pontos = ajudante.getConfig().getInt("Ajudante."+ajd);
			   	int total = pontos +1 ;
			   if(ajudante.getConfig().getString("Ajudante."+ajd) == null){
				   /* se n§o tiver o player na config ajudante, seta 1 */
					ajudante.set("Ajudante."+ajd, 1);
					ajudante.saveConfig();

			   }
			   
			   
			   
			   
			   
			   if(ajudante.getConfig().contains("Ajudante."+ajd)){
				   /*se tiver o player na config ajudante pega o numero de pontos q ele tem e soma com +1*/
	    		   ajudante.set("Ajudante."+ajd, total);
	    		   ajudante.saveConfig();
				   
			   }
				   
				   
				   
				   
				   
				   
				   
				   
			   }
  			   
  			   
  			   
			 }
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		return false;
	}

}
