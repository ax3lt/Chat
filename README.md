# Chat

              Wiki:
https://wiki.ax3lt.com/plugins/chat

Commands:
/chat reload

Permission:
All commands: chat.use
Reload config.yml: chat.reload
Use @everyone: chat.everyone

config.yml:

```YAML
#PLugin used for the chat
plugin: "essentials"

# When a player do /msg or aliases
message:
  sound: "minecraft:ui.cartography_table.take_result"
  volume: 2
  pitch: 1.5


chat:
 #How should the string be replaced if it finds the player?
  replaced: "&a@"
  sound: "minecraft:block.note_block.pling"
  volume: 2
  pitch: 2

tpa: # e tpahere
  sound: "minecraft:block.note_block.bit"
  volume: 2
  pitch: 2

everyone:
  replaced: "&c@"
  sound: "minecraft:block.note_block.pling"
  volume: 2
  pitch: 2

# Do you have any other plugin that colors the chat ( Ezcolors, Chatcolor.. )?
colored: false
#If colored is setted to false use this
end-string: "&r"

reload-message: "&l&2Config.yml reloaded!"

missing-message-error: "&cError: Missing message"

plugin-start: "&2Chat has been enabled!"
plugin-stop: "&4Chat has been disabled!"
```
