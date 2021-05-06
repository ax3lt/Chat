package it.ax3lt.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;




public class listeners implements Listener {
    Plugin plugin = main.getPlugin();

    public listeners(main instance) {
        plugin = instance;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player pp = e.getPlayer();
        String msg = e.getMessage();
        String oldColor = "§f";
        String[] arr = msg.split(" ");
        char[] ch = new char[msg.length()];
        for (int i = 0; i < msg.length(); i++) {
            ch[i] = msg.charAt(i);
        }



        if(pp.hasPermission("chat.use")) {
            if (ch[0] == '§') {
                oldColor = "§" + String.valueOf(ch[1]);
            }
            for (Player p : Bukkit.getOnlinePlayers())
            {

                for(int i = 0; i < arr.length; i++)
                {
                    if(arr[i].matches("\\b" + p.getName() + "\\b") || arr[i].matches("\\§[a-f]" + p.getName() + "\\b"))
                    {
                        if(isVanished(p))
                        {
                            return;
                        }
                        arr[i] = replaceAndSound(arr[i], main.getPlugin().getConfig().getString("chat.replaced"), p, oldColor);
                    }
                    if (pp.hasPermission("chat.everyone"))
                    {
                        if (arr[i].matches("@everyone") || arr[i].matches("\\§[a-f]" + "@everyone" + "\\b")) {
                            arr[i] = ChatColor.translateAlternateColorCodes('&', main.getPlugin().getConfig().getString("everyone.replaced") + "everyone" + oldColor);
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        p.playSound(p.getLocation(), main.getPlugin().getConfig().getString("chat.sound"), Float.parseFloat(main.getPlugin().getConfig().getString("everyone.volume")),
                                                Float.parseFloat(main.getPlugin().getConfig().getString("everyone.pitch")));
                                    }
                                }
                            }.runTaskLater(this.plugin, 0);
                        }
                    }
                }
                e.setMessage(String.join(" ", arr));
            }
        }
    }

    private boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }



    public String replaceAndSound(String stringa, String nuovo, Player p, String vecchioColore)
    {
        stringa = stringa.replaceAll(p.getName(), ChatColor.translateAlternateColorCodes('&', nuovo + p.getName()) + vecchioColore);
        p.playSound(p.getLocation(), main.getPlugin().getConfig().getString("chat.sound"),
                Float.parseFloat(main.getPlugin().getConfig().getString("chat.volume")), Float.parseFloat(main.getPlugin().getConfig().getString("chat.pitch")));

        return stringa;
    }

}
