package jp.co.systena.tigerscave.MonsterBattle4.application.model;

public class Trainer
{
	private String trainer_name;
	private int trainer_ID;
	private int poke_num;
	private int level;

	//なまえ
	public void setTrainer_name(String trainer_name)
	{
		this.trainer_name = trainer_name;
	}
	public String getTrainer_name()
	{
		return this.trainer_name;
	}

	//トレーナー番号
	public void setTrainer_ID(int trainer_ID)
	{
		this.trainer_ID = trainer_ID;
	}
	public int getTrainer_ID()
	{
		return this.trainer_ID;
	}

	// ポケモン番号
	public void setPoke_num(int poke_num)
	{
		this.poke_num = poke_num;
	}
	public int getPoke_num()
	{
		return this.poke_num;
	}

	// レベル
	public void setLevel(int level)
	{
		this.level = level;
	}
	public int getLevel()
	{
		return this.level;
	}


}
