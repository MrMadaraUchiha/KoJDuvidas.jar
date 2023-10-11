package pixel.ajd;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import pixel.shop.ShopInv;

import java.util.HashMap;

public class Comando implements CommandExecutor {
	   public HashMap<Player, Integer> cd = new HashMap<>();
	    public HashMap<Player, Integer> task = new HashMap<>();
	    public BukkitScheduler sh = Bukkit.getScheduler();
	public String Mensagem(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String arg : args) sb.append(arg).append(" ");	
		return sb.toString();
	}
	
	public String Mensagem2(String[] args) {
		  StringBuilder sb = new StringBuilder();
	   for (int i = 1; i< args.length; i++) {
			   sb.append(args[i]);
			   sb.append(" ");}
	   return sb.toString();
		
	}
	public static T_Config reports = new T_Config(Main.getPlugin(Main.class), "duvidas.yml");
//	public static T_Config pts = new T_Config(Main.getPlugin(Main.class), "pts.yml");
    

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
	

		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("§a§l[KingoNetwork] §7 apenas jogadores podem usar esse comando!");
			return true;
		}
		Player p = (Player)sender;
       if(cmd.getName().equalsIgnoreCase("duvida")) {
    	   int total = reports.getConfig().getInt("total");
    	   if(!(reports.getConfig().contains("total"))) {
    	   reports.set("total", 0);
    	   }
    	   if(reports.getConfig().contains("total")) {
        	   reports.set("total", total+1);
        	   }
    	 
    	   
//    	   if(p.hasPermission("koj.ajd")) {
//    		   p.sendMessage("§7§l[KingoNetwork] >>§c§l Acesso Negado apenas jogadores");
//    	   return true;
//    	   }
    	   if(args.length == 0 ) {
    			p.sendMessage("§7§l[KingoNetwork] >>§7 Digite /duvida (sua duvida)");
    			p.sendMessage("§7§l[KingoNetwork] >>§7 Exemplo:§a /duvida 'qual nível maximo?' ");
    			return true;
    	   }	
    	   if(reports.getConfig().getString("Player."+p.getName().toLowerCase()) != null ) {
			   p.sendMessage("§7§l[KingoNetwork] >>§c§l Você ja reportou aguarde uma resposta!");
			   return true;

    	   }
    	   if (args.length > 0) {
    		   String reporter = p.getName().toLowerCase();
    		   String mensagem = Mensagem(args);
    		   for(Player players : Bukkit.getOnlinePlayers()) {
    			   
    			   if(players.hasPermission("koj.ajd") || p.isOp()) {
    				   players.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7 O player:§a " + reporter);
    				   Bukkit.getConsoleSender().sendMessage("§a§l[AJD CONSOLE] §7§lo player "+ reporter + " reportou §a§l" + mensagem);

    					players.sendMessage("§7§l[§6§lDu§e§lvi§6§lda§7§l] >>§7Enviou uma Duvida:§a "+ mensagem);
    	    			 p.sendMessage(" ");

    					players.sendMessage("§7§l[§6§lDu§e§lvi§6§lda§7§l] >>§c digite /responder "+ reporter+ " resposta" );
    	    			 p.sendMessage(" ");

    					
    			   }
    		  
    		   }
    		   reports.set("Player."+reporter, mensagem);
    		   p.sendMessage("§7§l[KingoNetwork] >>§7 Sua duvida foi publicada!");
    		   p.sendMessage("§7§l[KingoNetwork] >>§7 Sua duvida: §a"+mensagem);
    		   reports.saveConfig();
    			   /* Acima todo comando Duvida com argumentos */
    			   
    		   }
    		   
    		   
    	   }

    	   
    	   if(cmd.getName().equalsIgnoreCase("perguntas")) {
    		   if(!p.hasPermission("koj.ajd")) {
    			   p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] >>§7"
    			   		+ " Você não tem permissão !");
    			   return true;
    			   
    		   }
    		   
    		   if(reports.getConfig().getConfigurationSection("Player") == null) {
			       p.sendMessage("§cSem duvidas ");

    			   return true;
    		   }
    		    
    			   

    			   ConfigurationSection cs = reports.getConfig().getConfigurationSection("Player");

    			   for(String report : cs.getKeys(false)){
    			       String str = cs.getString(report);
//    			       p.sendMessage(report+ ": " + str);
    			       
    			       p.sendMessage("§a§l≪≪§2 D U V I D A S §a§l≫≫");
    			       p.sendMessage(" ");
    			       p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Player :§a "+report);
    			       p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Duvida :§a "+str);
    			       p.sendMessage(" ");

    		   
    			   
    		   }
    	   
    	   }
    	   
    	   
    	   if(cmd.getName().equalsIgnoreCase("ajudante")) {
    		   
    		   if(!p.hasPermission("koj.ajd")) {
			       p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Você não tem permissao!" );

    			   return true;
    		   }
    		   
    		  if(args.length == 0) { 
		       p.sendMessage(" ");

			   ConfigurationSection cs = ComandoResponder.ajudante.getConfig().getConfigurationSection("Ajudante");
			   
			   for(String ajd : cs.getKeys(false)){
			       int str = cs.getInt(ajd);
//			       p.sendMessage(report+ ": " + str);
			       
	    		   p.sendMessage("§a§l≪≪§2 Ajudantes §a§l≫≫");
			       p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Ajudante :§a "+ajd);
			       p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Duvidas respondidas :§a "+str);
			       p.sendMessage(" ");
    		   
    		   
    		   
    		   
    		   
			   }
			   
    		  }
    		  if(args.length == 1) {
    		  if(args[0].equalsIgnoreCase("pag")) {
    			  ShopInv.open(p);
//    			  Inventory inv = Bukkit.createInventory(null,2*9, "Ajudante Pagamentos");
//    			  ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
//                  
//                  SkullMeta meta = (SkullMeta) skull.getItemMeta();
//                  meta.setOwner(p.getName().toLowerCase());
//                  meta.setDisplayName(ChatColor.GREEN + "'Ajudante " + p.getName().toLowerCase());
//                  skull.setItemMeta(meta);
//                  inv.setItem(4, skull);
//                  /*acima o item cabeça do player*/
//                  ItemStack esmeralda = new ItemStack(Material.EMERALD_BLOCK, 1);
//                  ItemMeta esmmeta = (ItemMeta) esmeralda.getItemMeta();
//                  esmmeta.setDisplayName("§aPagamento Minimo");
//                  ArrayList<String> lore = new ArrayList<>();
//                  lore.add("§bCusto: §e15 pontos");
//                  esmmeta.setLore(lore);
//                  esmeralda.setItemMeta(esmmeta);
//                  		inv.setItem(0, esmeralda);
//                  p.openInventory(inv);
                  
    		  }
    		  if(args[0].equalsIgnoreCase("anuncio")) {
    			  final Player pf = p;
                  
                  if(task.containsKey(p)) {
//                      p.sendMessage("§cAguarde "+cd.get(pf));
                      p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Aguarde para anunciar novamente!");
                      return true;
                  }
                 
                  int taskID = sh.scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
                     int numero = 120;
                    public void run() {
                        if(numero > 0) {
                            numero--;
                            cd.put(pf,numero);
                        }
                        if(numero == 0) {
                            sh.cancelTask(task.get(p));
                            cd.remove(pf);
                            task.remove(p);
 
                        }
                    }
                }, 0, 20);
               if(!task.containsKey(p)) {  
                  task.put(p, taskID);
                
                      Bukkit.broadcastMessage(" ");
                      Bukkit.broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> O Ajudante §a"+p.getName()+" §7esta respondendo duvidas");
                      Bukkit.broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Digite §a/duvida (sua duvida)");
                      Bukkit.broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Exemplo: §a/duvida qual o nível maximo?");
                      Bukkit.broadcastMessage(" ");
                     
               }
                  
    		  }
    		  if(args[0].equalsIgnoreCase("pontos")) {
    			  String nm = p.getName();
    			  String pontos = ComandoPonto.pts.getConfig().getString("Pontos."+nm);
    			 p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Atualmente você tem§a "+pontos+"§7 pontos.");
    		  }
    		  if(args[0].equalsIgnoreCase("help")) {
    			 p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Digite /ajudante anuncio (para anunciar que você esta respondendo duvidas)");
    			 p.sendMessage(" ");
    			 p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Digite /perguntas (ver a lista duvidas)");
    			 p.sendMessage(" ");

    			 p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Digite /responder <player> <resposta>(para responde-lo)");
    			 p.sendMessage(" ");

    			 p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Digite /ajudante pag (para pegar seu pagamento)");
    			 p.sendMessage(" ");
    			 p.sendMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §7>> Digite /ajudante pontos (para ver sua quantidade de pontos)");
    			 p.sendMessage(" ");

    		  }
    		 }
    		  
    		  
    		/* acima todo comando /ajudante */   
    	   }
    	   
    	   
    	  
    				
    				

		return false;
    	   }
	}
	


