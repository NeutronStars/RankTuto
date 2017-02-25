package fr.neutronstars.rank;

/**
 * Cette Class enum sert à initialiser les Grade du plugin.
 * 
 * @author NeutronStars
 * @since 1.0
 */
public enum RankList{

	/**
	 * Liste des Grades.
	 */
	ADMINISTRATOR(0, 100, "§4[Admin] ", "", " §8§l>> §3"),
	MODERATOR(2, 95, "§9[Modo] ", "", " §8>> §f"),
	HELPER(3, 70, "§2[Helper] ", " [Helper]", " §8> §f"),
	VIP(4, 40, "§a[VIP] ", " [VIP]", " §8> §f"),
	PLAYER(1, 1, "§7", "", " > ");
	
	/**
	 * power est la puissance/permission d'un Grade.
	 * id est la valeur unique d'un Grade
	 */
	private final int power, id;
	
	/**
	 * prefix est le String avant le pseudo du joueur.
	 * suffix est le String après le pseudo du joueur.
	 * chatSeparator est ce qui sépare le Pseudo et le message dans le Tchat.
	 */
	private final String prefix, suffix, chatSeparator;
	
	/**
	 * Seul constructeur qui permet d'initialiser les fields.
	 * 
	 * @param id Valeur unique d'un Grade.
	 * @param power Puissance/Permission d'un Grade. 
	 * @param prefix String affiché avant le pseudo du joueur.
	 * @param suffix String affiché après le pseudo du joueur.
	 * @param chatSeparator String qui sépare le pseudo du joueur et le message dans le tchat.
	 */
	private RankList(int id, int power, String prefix, String suffix, String chatSeparator){
		this.id = id;
		this.power = power;
		this.prefix = prefix;
		this.suffix = suffix;
		this.chatSeparator = chatSeparator;
	}
	
	/**
	 * Getter de l'id du grade.
	 * @return id
	 */
	public final int getId(){
		return id;
	}
	
	/**
	 * Getter du power du grade.
	 * @return power
	 */
	public final int getPower(){
		return power;
	}
	
	/**
	 * Getter du prefix du grade.
	 * @return prefix
	 */
	public final String getPrefix() {
		return prefix;
	}

	/**
	 * Getter du suffix du grade.
	 * @return suffix
	 */
	public final String getSuffix() {
		return suffix;
	}
	
	/**
	 * Getter de nom du grade.
	 * @return toString()
	 */
	public final String getName(){
		return this.toString();
	}

	/**
	 * Getter du chatSeparator du grade.
	 * @return chatSeparator
	 */
	public final String getChatSeparator() {
		return chatSeparator;
	}
}
