package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	public static Main plugin;
	

	@Override
	public void onEnable() {
		plugin = this;
		Registercmds();
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		
		Bukkit.getConsoleSender().sendMessage("§a§l[KOJ AJD] §7§l Plugin habilitado com sucesso!");

	}
	
	@Override
	public void onDisable() {

	
	
	}
	
	
	public void Registercmds() {
		getCommand("duvida").setExecutor(new Comando());
		getCommand("responder").setExecutor(new ComandoResponder());
		getCommand("perguntas").setExecutor(new Comando());
		getCommand("ajudante").setExecutor(new Comando());
		getCommand("ajd").setExecutor(new ComandoAdm());
		getCommand("ajdcheck").setExecutor(new ComandoLog());
		getCommand("pontos").setExecutor(new ComandoAdm());
		getCommand("darponto").setExecutor(new ComandoPonto());
		getCommand("tirarponto").setExecutor(new ComandoPonto());
		getCommand("verpontos").setExecutor(new ComandoVPonto());
		
	}
	
	
	
}
