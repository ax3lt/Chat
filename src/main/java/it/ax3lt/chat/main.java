package it.ax3lt.chat;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;




public class main extends JavaPlugin {

    public Map<String, String> msg = new HashMap<String, String>();
    int cont = 0;
    int cont1 = 0;


    String suono = getConfig().getString("message.sound");
    Float volume = Float.valueOf(getConfig().getString("message.volume"));
    Float pitch = Float.valueOf(getConfig().getString("message.pitch"));

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Chat");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new listeners(this), this);
        getCommand("chat").setTabCompleter(new autocopletamentoTab());
        this.saveDefaultConfig();
        //new UpdateChecker(this).checkForUpdate();

        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("plugin-start")));
    }


    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("plugin-stop")));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(sender instanceof Player)
        {
            Player p = (Player) sender;
            if (p.hasPermission("chat.reload"))
            {
                try {
                    if (commandLabel.equalsIgnoreCase("chat")) {
                        if (args.length > 0) {
                            if (args[0].equalsIgnoreCase("reload")) {
                                this.reloadConfig();
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("reload-message")));
                                return true;
                            }
                        }
                    }
                }catch (Exception exception)
                {
                    return true;
                }
            }

            if (commandLabel.equalsIgnoreCase("msg") || commandLabel.equalsIgnoreCase("w") ||
                    commandLabel.equalsIgnoreCase("m") || commandLabel.equalsIgnoreCase("t") ||
                    commandLabel.equalsIgnoreCase("pm") || commandLabel.equalsIgnoreCase("emsg") ||
                    commandLabel.equalsIgnoreCase("epm") || commandLabel.equalsIgnoreCase("tell") ||
                    commandLabel.equalsIgnoreCase("etell") || commandLabel.equalsIgnoreCase("whisper") ||
                    commandLabel.equalsIgnoreCase("ewhisper"))
            {

                String ricevitore;
                String messaggio = "";
                if (args.length == 0) {
                    p.getPlayer().performCommand(getConfig().getString("plugin") + ":msg");
                    return true;
                }
                if (args.length > 0) {
                    ricevitore = args[0];

                    for (int i = 1; i < args.length; i++) {
                        messaggio = messaggio + args[i] + " ";
                    }

                    if (messaggio != "" && messaggio != " ") {
                        p.getPlayer().performCommand(getConfig().getString("plugin") + ":msg" + " " + ricevitore + " " + messaggio);
                        if (getServer().getPlayer(ricevitore) != null) {
                            Player pricevitore = this.getServer().getPlayer(ricevitore);
                            msg.put(p.getName(), pricevitore.getName());
                            msg.put(pricevitore.getName(), p.getName());
                            try {
                                if (pricevitore.getName() != null) {
                                    suonoDestinatario(pricevitore, main.getPlugin().getConfig().getString("message.sound"), Float.parseFloat(main.getPlugin().getConfig().getString("message.volume")), Float.parseFloat(main.getPlugin().getConfig().getString("message.pitch")));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("missing-message-error")));
                        return true;
                    }


                }



                return false;
            }

            if(commandLabel.equalsIgnoreCase("tpa")) {

                if (args.length > 0) {
                    String ricevitore = args[0];
                    Player pricevitore = getServer().getPlayer(ricevitore);
                    p.performCommand(getConfig().getString("plugin") + ":tpa " + ricevitore);
                    try {
                        if (pricevitore.getDisplayName() != null) {
                            if (pricevitore.getDisplayName() != p.getDisplayName()) {
                                if (cont != 1) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            cont = 0;
                                        }

                                    }.runTaskLater(this, 20);

                                    suonoDestinatario(pricevitore, getConfig().getString("tpa.sound"), Float.parseFloat(getConfig().getString("tpa.volume")),
                                            Float.parseFloat(getConfig().getString("tpa.pitch")));

                                    cont = 1;
                                }

                            }
                        }
                        return true;
                    } catch (Exception e) {
                        return true;

                    }

                }

            }

            if (commandLabel.equalsIgnoreCase("tpahere"))
            {
                if (args.length > 0) {
                    String ricevitore = args[0];
                    Player pricevitore = getServer().getPlayer(ricevitore);
                    p.performCommand(getConfig().getString("plugin") + ":tpahere " + ricevitore);
                    try {
                        if (pricevitore.getDisplayName() != null) {
                            if (pricevitore.getDisplayName() != p.getDisplayName()) {
                                if (cont1 != 1) {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            cont1 = 0;
                                        }

                                    }.runTaskLater(this, 20);

                                    suonoDestinatario(pricevitore, getConfig().getString("tpa.sound"), Float.parseFloat(getConfig().getString("tpa.volume")),
                                            Float.parseFloat(getConfig().getString("tpa.pitch")));

                                    cont1 = 1;
                                }

                            }
                        }
                        return true;
                    } catch (Exception e) {
                        return true;

                    }

                }
            }

            if (commandLabel.equalsIgnoreCase("r")) {
                Player target = Bukkit.getPlayer(String.valueOf(msg.get(p.getName())));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i] + " ");
                }


                p.getPlayer().performCommand(getConfig().getString("plugin") + ":r " + sb);
                if (target == null) {
                    return true;
                } else {
                    suonoDestinatario(target, suono, volume, pitch);
                    return true;
                }

            }


        } else if (!(sender instanceof Player)) {
            String inviatore = getServer().getConsoleSender().getName();
            String ricevitore;
            String messaggio = "";
            String suono = getConfig().getString("message.sound");
            Float volume = Float.valueOf(getConfig().getString("message.volume"));
            Float pitch = Float.valueOf(getConfig().getString("message.pitch"));
            if (args.length == 0) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("plugin") + ":msg");
                return true;
            }
            if (args.length > 0) {

                ricevitore = args[0];
                for (int i = 1; i < args.length; i++) {
                    messaggio = messaggio + args[i] + " ";
                }
                if (messaggio != "" && messaggio != " ") {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("plugin") + ":msg" + " " + ricevitore + " " + messaggio);
                    if (getServer().getPlayer(ricevitore) != null) {
                        Player pricevitore = this.getServer().getPlayer(ricevitore);
                        if (pricevitore.getName() != null) {
                            suonoDestinatario(pricevitore, suono, volume, pitch);
                        }
                    }
                }
            } else {
                System.out.println(ChatColor.translateAlternateColorCodes('&', getConfig().getString("missing-message-error")));
            }
            return false;
        }
        return false;
    }

    public static void suonoDestinatario(Player receiver, String suono, float volume, float pitch)
    {
        if(isVanished(receiver))
        {
            return;
        }
        receiver.playSound(receiver.getLocation(), suono, volume, pitch);
    }

    private static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }
}