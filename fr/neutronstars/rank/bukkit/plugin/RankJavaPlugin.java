package fr.neutronstars.rank.bukkit.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neutronstars.rank.Rank;
import fr.neutronstars.rank.bukkit.command.RankCommand;
import fr.neutronstars.rank.bukkit.listener.PlayerListener;

/**
 * Cette Class extends {@link JavaPlugin} et permet de démarrer le plugin Rank.
 * 
 * @author NeutronStars
 * @since 1.0
 */
public final class RankJavaPlugin extends JavaPlugin{

	/**
	 * Class principal {@link Rank}
	 */
	private Rank rank;
	
	/**
	 * Methode utilisé lors du chargement du plugin.
	 */
	public final void onLoad() {
		rank = new Rank(this);
	}
	
	/**
	 * Methode utilisé une fois le plugin activé.
	 */
	public final void onEnable() {
		rank.initScoreboard();
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(rank), this);
		getCommand("rank").setExecutor(new RankCommand(rank));
	}
	
	/**
	 * Methode utilisé lors que le plugin s'arrête.
	 */
	public final void onDisable() {
		
	}
}
