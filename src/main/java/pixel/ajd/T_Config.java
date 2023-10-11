package pixel.ajd;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
 
public class T_Config {
    public T_Config(JavaPlugin plugin, String nome) {
        this.plugin = plugin;
        setName(nome);
        reloadConfig();
    }
   
    private JavaPlugin plugin;
    private String name;
    private File file;
    public JavaPlugin getPlugin() {
        return plugin;
    }
    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public File getFile() {
        return file;
    }
    public YamlConfiguration getConfig() {
        return config;
    }
    private YamlConfiguration config;
    public void saveConfig() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveDefault() {
        getConfig().options().copyDefaults(true);
    }
    public void saveDefaultConfig() {
        getPlugin().saveResource(getName(), false);
    }
    public void reloadConfig() {
        file = new File(getPlugin().getDataFolder(),getName());
        config = YamlConfiguration.loadConfiguration(getFile());
       
    }
    public void deleteConfig() {
        getFile().delete();
    }
    public boolean existeConfig() {
        return getFile().exists();
    }
   
    public String getString(String path) {
        return getConfig().getString(path);
    }
   
    public int getInt(String path) {
        return getConfig().getInt(path);
    }
   
    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }
   
    public double getDouble(String path) {
        return getConfig().getDouble(path);
    }
   
    public List<?> getList(String path){
        return getConfig().getList(path);
    }
    public boolean contains(String path) {
        return getConfig().contains(path);
    }
   
    public void set(String path, Object value) {
        getConfig().set(path, value);
    }
    
    
}