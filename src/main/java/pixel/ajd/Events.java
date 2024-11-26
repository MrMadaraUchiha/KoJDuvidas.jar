package pixel.ajd;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

	// Configuração para armazenar as dúvidas dos jogadores
	private final T_Config reports = ComandoResponder.getReportsConfig();

	/**
	 * Evento acionado quando um jogador entra no servidor.
	 * Checa se ele possui dúvidas pendentes e avisa os jogadores com permissão.
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName().toLowerCase();

		// Verifica se o jogador tem dúvidas pendentes
		if (reports.getConfig().contains("Player." + playerName)) {
			// Mensagem para o jogador que entrou, se ele for staff ou ajudante
			if (player.hasPermission("koj.staff") || player.hasPermission("koj.ajd")) {
				player.sendMessage("§7§l[KingoNetwork] >> §7Você possui dúvidas pendentes. Use o comando §c/perguntas §7para checar.");
			}

			// Aviso para outros jogadores com permissão (staff/ajudantes)
			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if (onlinePlayer.hasPermission("koj.staff") || onlinePlayer.hasPermission("koj.ajd")) {
					onlinePlayer.sendMessage("§7§l[KingoNetwork] >> §7O jogador §a" + playerName + " §7possui uma dúvida pendente.");
					onlinePlayer.sendMessage("§7Use o comando §c/perguntas §7para checar.");
				}
			}
		} else if (!Comando.reports.getConfig().getConfigurationSection("Player").getKeys(false).isEmpty()) {
			player.sendMessage("§7§l[KingoNetwork] >> §7Existem dúvidas pendentes. Use o comando §c/perguntas §7para checar.");
		}
	}
}
