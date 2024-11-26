package pixel.ajd;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RespostaOfflineListener implements Listener {
    private final T_Config log = ComandoResponder.getLogConfig();
    private final T_Config reports = ComandoResponder.getReportsConfig();
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName().toLowerCase();

        // Verificar se existem respostas offline para o jogador
        if (reports.getConfig().contains("RespostasOffline." + playerName)) {
            String ajudante = reports.getConfig().getString("RespostasOffline." + playerName + ".ajudante");
            String mensagem = reports.getConfig().getString("RespostasOffline." + playerName + ".mensagem");

            // Enviar a resposta ao jogador
            player.sendMessage("§7§l[KingoNetwork] >>§7 Sua dúvida foi respondida por: §a" + ajudante);
            player.sendMessage("§7§l[KingoNetwork] >>§7 Resposta: §a" + mensagem);

            // Registrar no log
            String logMessage = "&a O Ajudante &b" + ajudante +
                    " &arespondeu o Jogador &b" + playerName +
                    "&e com a resposta: &6" + mensagem;
            int totalReports = reports.getConfig().getInt("total", 0);
            log.set("Player." + ajudante + "." + totalReports, logMessage);
            log.saveConfig();

            // Remover a dúvida da seção "Player" em reports.yml
            reports.getConfig().set("Player." + playerName, null);
            reports.saveConfig();

            // Remover a entrada de RespostasOffline
            reports.getConfig().set("RespostasOffline." + playerName, null);
            reports.saveConfig();
        }
    }
}
