package fr.neutronstars.rank.bukkit.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import fr.neutronstars.rank.Rank;
import fr.neutronstars.rank.RankList;

/**
 * Cette Class implement {@link CommandExecutor} et {@link TabCompleter}
 * Elle sert d'executeur pour la commande /rank
 * 
 * @author NeutronStars
 * @since 1.0
 */
public final class RankCommand implements CommandExecutor, TabCompleter {

	/**
	 * Class principal {@link Rank}
	 */
	private final Rank rank;
	
	/**
	 * Seul constructeur qui permet d'initialiser le field {@link Rank}
	 * @param rank
	 */
	public RankCommand(Rank rank) {
		this.rank = rank;
	}
	
	/**
	 * Methode pour l'execution de la commande /rank
	 * 
	 * @param s Le joueur ou la console qui envoi la commande.
	 * @param c Class de la commande.
	 * @param l Nom de la command.
	 * @param a Tableau des arguments qui suit le nom de la commande.
	 */
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
 		if(s instanceof Player)
 			if(rank.hasPowerInf((Player)s, 90))
 				return sendMessage(s, "§cVous n'avais pas la permission.");
 		
 		if(a.length < 2) return sendMessage(s, " §c/rank <Player> <Rank>");
 		
		OfflinePlayer target = Bukkit.getOfflinePlayer(a[0]);		
 		if(target == null) return sendMessage(s, "§cLe joueur n'a pas été trouvé.");
 		
 		String uuid = target.getUniqueId().toString();
 		if(!rank.getConfig().contains(uuid)) return sendMessage(s, "§cLe joueur n'a pas été trouvé");
 		
 		RankList rankList = null;
 		try{
 			rankList = rank.getRankById(Integer.parseInt(a[1]));
 		}catch(NumberFormatException nbe){
 			try{
 				rankList = RankList.valueOf(a[1].toUpperCase());
 			}catch(Exception e){
 				return sendMessage(s, "§cLe rank n'a pas été trouvé.");
 			}
 		}
 		
 		rank.changeRank(uuid, rankList);
 		
 		if(target.isOnline()){
 			sendMessage(target.getPlayer(), "§9Votre grade a été modifier.");
 			rank.deletePlayer(target.getPlayer());
 			rank.loadPlayer(target.getPlayer());
 		}
 		
 		return sendMessage(s, "§6"+target.getName()+" §2a bien obtenu sont grade §5"+rankList.getName().toLowerCase());
	}

	/**
	 * Methode d'optimisation qui permet de retourner la valeur boolean true
	 * et d'envoyer un message à un joueur ou a la console.
	 * 
	 * @param s Joueur/Console auquel le message devra etre envoyé.
	 * @param msg Le message a envoyer au joueur/console
	 * @return true obligatoirement.
	 */
	private boolean sendMessage(CommandSender s, String msg){
		s.sendMessage(rank.getPrefix()+msg);
		return true;
	}
	
	/**
	 * Methode pour completer la commande /rank
	 * 
	 * @param s Le joueur ou la console qui saisi la commande.
	 * @param c Class de la commande qui est en train d'être saisi.
	 * @param l Nom de la command.
	 * @param a Tableau des arguments qui suit le nom de la commande.
	 */
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a){
		List<String> tabComplete = Lists.newArrayList();
		if(a.length == 2){
			for(RankList rankList : RankList.values())
				if(rankList.getName().toLowerCase().startsWith(a[1].toLowerCase()))
					tabComplete.add(rankList.getName());
			return tabComplete;
		}
		return null;
	}
}
