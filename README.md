# FeatherSpawners

FeatherSpawners adds the ability for players to mine spawners with a silk touch pickaxe.  
With permissions a player can also change spawners type while holding them in their hand.  

This plugin avoids the use of NMS code (which would require a rewrite for each MC version).  
A server admin must first designate spawners before a spawner can be set.  
using vanilla creative mode, an admin should create for example, a spider spawner.  
The admin should then CTRL-wheel click on the spawner to obtain the NBT item.  
The admin should then use /spawner designate spider while holding the spawner.  
The spider spawner is now designated and can now be used in a set command.

### Permission Nodes:
    feather.spawners.designate   -  /spawner designate <mob>       -  Assign a spawner to config.
    feather.spawners.set         -  /spawner set <mob>             -  Set a spawner type.
    feather.spawners.give        -  /spawner give <player> <mob>   -  Give a player a spawner.
    feather.spawners.silktouch   -                                 -  Allow collecting spawners.
    
