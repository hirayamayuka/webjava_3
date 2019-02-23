package jp.co.systena.tigerscave.MonsterBattle4.application.model;

public class DefaultPoke
{
	private int poke_num;
	private String poke_name;
	private int poke_type1;
	private String poke_typename1;
	private int poke_type2;
	private String poke_typename2;
	private String rare;
	private int nextpoke_num;
	private int evolution_lv;
	private int HP;
	private int Attack;
	private int Speed;

	private int level;

	// ポケモンNo.
	public void setPoke_num(int poke_num)
	{
		this.poke_num = poke_num;
	}
	public int getPoke_num()
	{
		return this.poke_num;
	}

	// ポケモン名
	public void setPoke_name(String poke_name)
	{
		this.poke_name = poke_name;
	}
	public String getPoke_name()
	{
		return this.poke_name;
	}

	// ポケモンタイプ1
	public void setPoke_type1(int poke_type1)
	{
		this.poke_type1 = poke_type1;
	}
	public int getPoke_type1()
	{
		return this.poke_type1;
	}

	// ポケモンタイプ名1
	public void setPoke_typename1(String poke_typename1)
	{
		this.poke_typename1 = poke_typename1;
	}
	public String getPoke_typename1()
	{
		return this.poke_typename1;
	}

	// ポケモンタイプ2
	public void setPoke_type2(int poke_type2)
	{
		this.poke_type2 = poke_type2;
	}
	public int getPoke_type2()
	{
		return this.poke_type2;
	}

	// ポケモンタイプ名2
	public void setPoke_typename2(String poke_typename2)
	{
		this.poke_typename2 = poke_typename2;
	}
	public String getPoke_typename2()
	{
		return this.poke_typename2;
	}

	// ポケモンタイプ名2
	public void setRare(String rare)
	{
		this.rare = rare;
	}
	public String getRare()
	{
		return this.rare;
	}

	// 進化ポケモン
	public void setNextpoke_num(int nextpoke_num)
	{
		this.nextpoke_num = nextpoke_num;
	}
	public int getNextpoke_num()
	{
		return this.nextpoke_num;
	}

	// 進化レベル
	public void setEvolution_lv(int evolution_lv)
	{
		this.evolution_lv = evolution_lv;
	}
	public int getEvolution_lv()
	{
		return this.evolution_lv;
	}

	// HP
	public void setHP(int HP)
	{
		this.HP = HP;
	}
	public int getHP()
	{
		return this.HP;
	}

	// Attack
	public void setAttack(int Attack)
	{
		this.Attack = Attack;
	}
	public int getAttack()
	{
		return this.Attack;
	}

	// Speed
	public void setSpeed(int Speed)
	{
		this.Speed = Speed;
	}
	public int getSpeed()
	{
		return this.Speed;
	}

	// level
	public void setLevel(int level)
	{
		this.level = level;
	}
	public int getLevel()
	{
		return this.level;
	}
}
