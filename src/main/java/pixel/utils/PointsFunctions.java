package pixel.utils;

import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;
import static pixel.ajd.ComandoPonto.pts;

public class PointsFunctions {
    // Método para dar pontos a um jogador
    public boolean adicionarPontos(Player ajudante, int pontos) {
        if (ajudante == null) {
            getLogger().info("Null tlgd");
            return true;
        }
        String playerPath = "Pontos." + ajudante.getName();
        if(pts.getConfig().contains(playerPath)) {
            int pontosAtuais = pts.getConfig().getInt(playerPath, 0); // Pega os pontos atuais ou 0 se não existir
            int total = pontosAtuais + pontos;
            // Atualiza a configuração
            pts.set(playerPath, total);
            getLogger().info(playerPath);

            pts.saveConfig();
            return true;
        }
        return true;
    }

}
