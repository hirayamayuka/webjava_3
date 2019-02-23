package jp.co.systena.tigerscave.MonsterBattle4.application.model;

public class Form
{
	private String trainer_name;
	private int num;
	private int poke_num;

	// トレーナー名
	public void setTrainer_name(String trainer_name)
	{
		this.trainer_name = trainer_name;
	}
	public String getTrainer_name()
	{
		return this.trainer_name;
	}
	/*/ プレイヤー名
		public void setPoke_name(String poke_name)
	{
		this.poke_name = poke_name;
	}
	public String getPoke_name()
	{
		return this.poke_name;
	}
	*/

	// num
	public void setNum(int num)
	{
		this.num = num;
	}
	public int getNum()
	{
		return this.num;
	}

	// poke_num
	public void setPoke_num(int poke_num)
	{
		this.poke_num = poke_num;
	}
	public int getPoke_num() {
		return this.poke_num;
	}

}
