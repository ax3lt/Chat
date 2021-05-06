package it.ax3lt.chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

class autocopletamentoTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> comandi = new ArrayList<>();
            comandi.add("reload");
            return comandi;
        }
        return null;
    }
}

