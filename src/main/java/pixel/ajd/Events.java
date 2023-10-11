package pixel.ajd;


import com.sagan.kojajd.Criar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener{
	
	@EventHandler
	public	void onShop(InventoryClickEvent e) {
		if(!(e.getWhoClicked() instanceof Player)) {
			return;
		}
	Player p = (Player)e.getWhoClicked();
	if(e.getView().getTitle().equalsIgnoreCase("§aAjudante§c Pagamentos")) {
		e.setCancelled(true);
		if(e.getCurrentItem()==null)return;
		if(e.getCurrentItem().getType()==Material.AIR)return;
		if(e.getCurrentItem().getItemMeta().getDisplayName()==null)return;
		ItemStack item = e.getCurrentItem();
		
		switch (item.getItemMeta().getDisplayName()) {
		case "§aSal§rio Minimo":
			
			if(ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName()) > 4) {
				int ponto = ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName());
				p.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK,6));
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				
				
				
				
				ComandoPonto.pts.set("Pontos."+p.getName(), ponto-5);
				ComandoPonto.pts.saveConfig();
				p.closeInventory();
				
			}
			if(ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName()) < 5) {
				p.sendMessage("§cVoc§ n§o possui pontos suficientes");
				p.closeInventory();

			}
			
			
			break;
	
		case"§aAjudante Status":
				int pontos = ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName());
				 int res = ComandoResponder.ajudante.getConfig().getInt("Ajudante."+p.getName());
			p.sendMessage("");
			p.sendMessage("§7§l[KingoNetwork] >>§7 Voc§ possui §a "+pontos+" §7pontos");
			p.sendMessage("§7§l[KingoNetwork] >>§7 Voc§ respondeu §a "+res+" §7perguntas");
			p.sendMessage("§cdigite /ajudante help");

			
			
			
			
			
			
			
			break;
case "§cTrof§u do Ajudante":
			
			if(ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName()) > 99) {
				int ponto = ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName());
				ItemStack pepi = Criar.add(Material.GOLD_NUGGET, "§cTrof§u do Ajudante", new String[] {"§bCusto 100 pontos", "§7- Tr§feu do ajudante "+ p.getName(),"§7agradou a staff com suas respostas :)"});

				p.getInventory().addItem(pepi);
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
				
				
				
				
				ComandoPonto.pts.set("Pontos."+p.getName(), ponto-100);
				ComandoPonto.pts.saveConfig();
				p.closeInventory();
				
			}
			if(ComandoPonto.pts.getConfig().getInt("Pontos."+p.getName()) < 100) {
				p.sendMessage("§cVoc§ n§o possui pontos suficientes");
				p.closeInventory();

			}
			
			
			break;
		default:
			break;
		}
		
		
	}
}
	@EventHandler
	public void onjoin(PlayerJoinEvent e) {
		Player pls= e.getPlayer();
		String nome = pls.getName().toLowerCase();
		if(Comando.reports.getConfig().contains("Player."+nome)) {
			pls.sendMessage("§7§l[KingoNetwork] >>§7 Voc§ tem uma duvida pendente, Aguarde uma resposta.");
			for(Player plss : Bukkit.getOnlinePlayers()) {
				
				plss.sendMessage("§7§l[KingoNetwork] >>§7 O jogador "+nome+" cont§m uma duvida pendente");
				plss.sendMessage("§cdigite /perguntas ");
				plss.sendMessage(" ");
				
			}
			
			
		}
		if(!Comando.reports.getConfig().contains("Player."+nome)) {
			pls.sendMessage("");
			pls.sendMessage("§7§l[KingoNetwork] >>§7 Duvidas sobre o servidor?");
			pls.sendMessage("§7§l[KingoNetwork] >>§7 Digite /duvida (sua duvida)");
			pls.sendMessage("");

			//			pls.sendMessage("§c§l!§cAtencao§c§l!§c n§o abuse com duvidas desnecessarias, evite ser punido!");
		}
		
	}
	
	

}
