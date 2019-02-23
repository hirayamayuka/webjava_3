package jp.co.systena.tigerscave.MonsterBattle4.application.model;

public class Field
{
	private String yuurifield; // 有利フィールドタイプ
	private String furifield;  // 不利フィールドタイプ

	// 有利フィールド
	public void setYuuriField(String yuurifield)
	{
		this.yuurifield = yuurifield;
	}
	public String getYuuriField()
	{
		return this.yuurifield;
	}
	//なまえ
	public void setFuriField(String furifield)
	{
		this.furifield = furifield;
	}
	public String getFuriField()
	{
		return this.furifield;
	}
}
