package jp.co.systena.tigerscave.MonsterBattle4.application.model;

public class Monster
{
	private String name; 	//なまえ
	private String mytype; //自分のタイプ
	private int hp;			//たいりょく
	private int attack;		//こうげき
	private int speed;			//すばやさ
	private String poke_typename1; // 有利タイプ
	private String poke_typename2;  // 不利タイプ

	//なまえ
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	// 自分のタイプ
	public void setMytype(String mytype)
	{
		this.mytype = mytype;
	}
	public String getMytype()
	{
		return this.mytype;
	}

	//たいりょく
	public void setHp(int hp)
	{
		this.hp = hp;
	}
	public int getHp()
	{
		return this.hp;
	}

	//こうげき
	public void setAttack(int attack)
	{
		this.attack = attack;
	}
	public int getAttack()
	{
		return this.attack;
	}

	//すばやさ
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public int getSpeed()
	{
		return this.speed;
	}

	//タイプ1
	public void setPoke_typename1(String poke_typename1)
	{
		this.poke_typename1 = poke_typename1;
	}
	public String getPoke_typename1()
	{
		return this.poke_typename1;
	}
	//タイプ2
	public void setPoke_typename2(String poke_typename2)
	{
		this.poke_typename2 = poke_typename2;
	}
	public String getPoke_typename2()
	{
		return this.poke_typename2;
	}
}
