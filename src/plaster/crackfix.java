package plaster;

import java.util.HashSet;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import com.degoos.wetsponge.WetSponge;
import com.degoos.wetsponge.parser.player.PlayerParser;
import com.degoos.wetsponge.parser.entity.SpigotEntityParser;
import com.degoos.wetsponge.entity.WSEntity;
import com.degoos.wetsponge.entity.living.player.WSPlayer;
import com.degoos.wetsponge.enums.EnumDamageType;
import com.degoos.wetsponge.event.entity.WSEntityDamageByEntityEvent;

public class crackfix extends JavaPlugin implements org.bukkit.event.Listener{
	@Override
    public void onEnable () {
        Bukkit.getPluginManager().registerEvents(this, this);
    }
	@EventHandler
    public void onWeaponDamage(WeaponDamageEntityEvent event) {
        WSPlayer damager = event.getPlayer() == null ? null : PlayerParser.getPlayer(event.getPlayer().getUniqueId()).orElse(null);
        WSEntity entity = SpigotEntityParser.getWSEntity(event.getVictim());

        WSEntityDamageByEntityEvent wetSpongeEvent = new WSEntityDamageByEntityEvent(entity, event.getDamage(), new HashSet<>(), EnumDamageType.CUSTOM, damager, event
            .getDamage());
        WetSponge.getEventManager().callEvent(wetSpongeEvent);
        event.setCancelled(wetSpongeEvent.isCancelled());
    }
}