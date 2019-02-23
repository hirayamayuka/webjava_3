package jp.co.systena.tigerscave.MonsterBattle4.application.model;

public class SelectType extends Monster
{
	public SelectType(String name , String mytype , int hp , int attack , int speed , String poke_typename1  , String poke_typename2)
	{
		//super(name,hp,speed,advantageous_type,unfavorable_type);
		super.setName(name);
		super.setMytype(mytype);
		super.setHp(hp);
		super.setAttack(attack);
		super.setSpeed(speed);
		super.setPoke_typename1(poke_typename1);
		super.setPoke_typename2(poke_typename2);
	}

/*	@Override
	public void attack()
	{
		System.out.println("フシギダネのアタック");
	}

*/
}

