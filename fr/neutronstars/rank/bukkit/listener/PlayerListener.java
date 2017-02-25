package fr.neutronstars.rank.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.neutronstars.rank.Rank;
import fr.neutronstars.rank.RankList;

/**
 * Cette Class implement {@link Listener} pour les events qui extends de {@link PlayerEvent}
 * 
 * @author NeutronStars
 * @since 1.0
 */
public final class PlayerListener implements Listener{

	/**
	 * Class principal {@link Rank}
	 */
	private final Rank rank;
	
	/**
	 * Seul constructeur qui permet d'initialiser le field {@link Rank}
	 * @param rank
	 */
	public PlayerListener(Rank rank) {
		this.rank = rank;
	}
	
	/**
	 * Methode permettant d'utiliser l'event {@link PlayerJoinEvent}
	 * @param pje
	 */
	@EventHandler
	private void playerJoin(PlayerJoinEvent pje){
		rank.loadPlayer(pje.getPlayer());
		pje.getPlayer().setScoreboard(rank.getScoreboard());
	}
	
	/**
	 * Methode permettant d'utiliser l'event {@link PlayerQuitEvent}
	 * @param pje
	 */
	@EventHandler
	private void playerQuit(PlayerQuitEvent pqe){
		rank.deletePlayer(pqe.getPlayer());
	}
	
	/**
	 * Methode permettant d'utiliser l'event {@link AsyncPlayerChatEvent}
	 * @param pje
	 */
	@EventHandler
	private void playerChat(AsyncPlayerChatEvent pce){
		RankList rankList = rank.getPlayerRank(pce.getPlayer());
		pce.setFormat(rankList.getPrefix()+pce.getPlayer().getName()+rankList.getSuffix()+rankList.getChatSeparator()+pce.getMessage());
	}
}
