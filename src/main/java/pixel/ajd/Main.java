package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;

	/**
	 * Retorna a instância do plugin.
	 */
	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		// Registrar comandos e eventos
		registerCommands();
		registerEvents();

		// Mensagem no console indicando sucesso
		Bukkit.getConsoleSender().sendMessage("§a§l[KOJ AJD] §7§l Plugin habilitado com sucesso!");
	}

	@Override
	public void onDisable() {
		// Limpeza ou desligamentos necessários (se houver)
		Bukkit.getConsoleSender().sendMessage("§c§l[KOJ AJD] §7§l Plugin desabilitado.");
	}

	/**
	 * Registra todos os comandos do plugin.
	 */
	private void registerCommands() {
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

	/**
	 * Registra os eventos do plugin.
	 */
	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new Events(), this);
		getServer().getPluginManager().registerEvents(new RespostaOfflineListener(), this);
	}
}
