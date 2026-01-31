package at.xoola.chunkyoffline.language;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SimpleLanguageManager {
    
    private final JavaPlugin plugin;
    private final Gson gson;
    private final Map<String, JsonObject> languages;
    private String defaultLanguage;
    
    public SimpleLanguageManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new Gson();
        this.languages = new HashMap<>();
        this.defaultLanguage = "de"; // Default to German
        
        loadLanguages();
        loadConfig();
    }
    
    private void loadConfig() {
        // Load language setting from config.yml
        String configLang = plugin.getConfig().getString("language", "de");
        if (languages.containsKey(configLang)) {
            this.defaultLanguage = configLang;
        } else {
            plugin.getLogger().warning(getMessage("language.not_found", configLang));
            this.defaultLanguage = "de";
        }
        plugin.getLogger().info(getMessage("language.using", this.defaultLanguage));
    }
    
    private void loadLanguages() {
        // Load built-in languages from resources
        loadLanguageFromResource("en");
        loadLanguageFromResource("de");
        
        plugin.getLogger().info(getMessage("language.loaded", languages.size(), String.join(", ", languages.keySet())));
    }
    
    private void loadLanguageFromResource(String langCode) {
        try {
            InputStreamReader reader = new InputStreamReader(
                plugin.getResource("lang/" + langCode + ".json"), 
                StandardCharsets.UTF_8
            );
            JsonObject langData = gson.fromJson(reader, JsonObject.class);
            languages.put(langCode, langData);
            reader.close();
        } catch (Exception e) {
            plugin.getLogger().warning(getMessage("language.load_error", langCode, e.getMessage()));
        }
    }
    
    /**
     * Get a translated message for the default language
     */
    public String getMessage(String key, Object... args) {
        return getMessage(defaultLanguage, key, args);
    }
    
    /**
     * Get a translated message for a specific language
     */
    public String getMessage(String langCode, String key, Object... args) {
        JsonObject langData = languages.get(langCode);
        
        // Fallback to default language if requested language not found
        if (langData == null && !langCode.equals(defaultLanguage)) {
            langData = languages.get(defaultLanguage);
        }
        
        // Fallback to English if default language not found
        if (langData == null && !defaultLanguage.equals("en")) {
            langData = languages.get("en");
        }
        
        // Last resort: return key if nothing found
        if (langData == null) {
            return "[MISSING LANG: " + key + "]";
        }
        
        // Get the translated text
        String message = langData.has(key) ? langData.get(key).getAsString() : "[MISSING KEY: " + key + "]";
        
        // Apply formatting if arguments provided
        if (args.length > 0) {
            try {
                message = MessageFormat.format(message, args);
            } catch (Exception e) {
                plugin.getLogger().warning(getMessage("language.format_error", key, e.getMessage()));
            }
        }
        
        return message;
    }
    
    /**
     * Send a translated message to a command sender
     */
    public void sendMessage(CommandSender sender, String key, Object... args) {
        String message = getMessage(key, args);
        String prefix = getMessage("prefix");
        sender.sendMessage(prefix + " §f" + message);
    }
    
    /**
     * Send a translated message to a player
     */
    public void sendPlayerMessage(Player player, String key, Object... args) {
        String message = getMessage(key, args);
        String prefix = getMessage("prefix");
        player.sendMessage(prefix + " §f" + message);
    }
    
    /**
     * Get default language code
     */
    public String getDefaultLanguage() {
        return defaultLanguage;
    }
}