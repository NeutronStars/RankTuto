package fr.neutronstars.rank;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.collect.Maps;

/**
 * Cette Class est la class principal du plugin.
 * 
 * @author NeutronStars
 * @since 1.0
 */
public final class Rank{
	
	/**
	 * Permet de stocker les grades des joueurs connecté.
	 */
	private final Map<String, RankList> playerRanks = Maps.newHashMap();
	
	/**
	 * Permet d'enregistrer les Teams pour afficher les Grades.
	 */
	private Scoreboard scoreboard;
	
	/**
	 * Permet de garder une instance du Plugin.
	 */
	private final Plugin plugin;
	
	/**
	 * Permet de set un prefix au message envoyé par le plugin.
	 */
	private final String prefix = "§8[§4Rank§8] ";

	/**
	 * Permet d'enregistrer les grades de joueur.
	 */
	private FileConfiguration config;
	
	/**
	 * Endroit ou ce trouve le fichier yml de la config.
	 */
	private File file;
	
	/**
	 * Seule constructeur qui permet d'initialiser le plugin ainsi que de créer la config.
	 * @param plugin
	 */
	public Rank(Plugin plugin){
		this.plugin = plugin;
		initConfig();
	}
	
	/**
	 * Récupere la class {@link Plugin}
	 * @return plugin
	 */
	public final Plugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Recupére le prefix pour les debut des message envoyé par le plugin.
	 * @return prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Recupère le Scoreboard ou sont enregistrer les Teams pour les Grades.
	 * @return scoreboard
	 */
	public final Scoreboard getScoreboard(){
		return scoreboard;
	}
	
	/**
	 * Recupère la config pour enregistrer les grades des joueur.
	 * @return config
	 */
	public final FileConfiguration getConfig() {
		return config;
	}
	
	/**
	 * Creer le dossier Rank dans le dossier plugin puis le fichier rank.yml dans le dossier Rank
	 * Ensuite Chargement de la config.
	 */
	private void initConfig(){
		File f = new File("plugins/Rank");
		if(!f.exists()) f.mkdirs();
		file = new File(f, "rank.yml");
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException ioe){ ioe.printStackTrace();}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	/**
	 * Enregistrer les Teams des Grades du {@link RankList} dans le scoreboard
	 */
	public void initScoreboard(){
		if(scoreboard != null) throw new UnsupportedOperationException("Le scoreboard est deja initialise.");
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		for(RankList rankList : RankList.values()){
			Team team = scoreboard.registerNewTeam(rankList.getName());
			team.setPrefix(rankList.getPrefix());
			team.setSuffix(rankList.getSuffix());
		}
	}
	
	/**
	 * Enregistre le rank du joueur dans la Map playerRanks
	 * puis insert le joueur dans la bonne team en fonction de sont Grade.
	 * @param player
	 */
	public void loadPlayer(Player player){
		String uuid = player.getUniqueId().toString();
		if(playerRanks.containsKey(uuid)) return;
		if(!config.contains(uuid)) changeRank(uuid, RankList.PLAYER);
		
		playerRanks.put(uuid, getRankById(config.getInt(uuid)));
		scoreboard.getTeam(playerRanks.get(uuid).getName()).addEntry(player.getName());
	}
	
	/**
	 * Supprime le joueur de la Map playerRanks.
	 * @param player
	 */
	public void deletePlayer(Player player){
		if(!playerRanks.containsKey(player.getUniqueId().toString())) return;
		playerRanks.remove(player.getUniqueId().toString());
	}
	
	/**
	 * Recupère le {@link RankList} d'un joueur.
	 * @param player
	 * @return rank du player
	 */
	public RankList getPlayerRank(Player player){
		if(!playerRanks.containsKey(player.getUniqueId().toString())) loadPlayer(player);
		return playerRanks.get(player.getUniqueId().toString());
	}
	
	/**
	 * Recupère le {@link RankList} en fonction de l'id
	 * @param id
	 * @return rank par raport à l'id.
	 */
	public RankList getRankById(int id){
		for(RankList rankList : RankList.values()){
			if(rankList.getId() == id) return rankList;
		}
		return RankList.PLAYER;
	}
	
	/**
	 * Sauvegarde le fichier rank.yml
	 */
	public void saveConfig(){
		try{
			config.save(file);
		}catch(IOException ioe){ioe.printStackTrace();}
	}
	
	/**
	 * Permet de savoir si le power du Grade d'un player est egale au power dans les paramètres
	 * @param player
	 * @param power
	 * @return
	 */
	public boolean hasPower(Player player, int power){
		return (getPlayerRank(player).getPower() == power);
	}
	
	/**
	 * Permet de savoir si le power du Grade d'un player est plus grand que le power dans les paramètres
	 * @param player
	 * @param power
	 * @return
	 */
	public boolean hasPowerSup(Player player, int power){
		return (getPlayerRank(player).getPower() > power);
	}
	
	/**
	 * Permet de savoir si le power du Grade d'un player est plus petit que le power dans les paramètres
	 * @param player
	 * @param power
	 * @return
	 */
	public boolean hasPowerInf(Player player, int power){
		return (getPlayerRank(player).getPower() < power);
	}

	/**
	 * Permet de changer le grade dans le fichier rank.yml
	 * @param uuid
	 * @param rankList
	 */
	public void changeRank(String uuid, RankList rankList){
		config.set(uuid, rankList.getId());
		saveConfig();
	}
}
