package jp.co.systena.tigerscave.MonsterBattle4.application.controller;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.systena.tigerscave.MonsterBattle4.application.model.DefaultPoke;
import jp.co.systena.tigerscave.MonsterBattle4.application.model.Form;
import jp.co.systena.tigerscave.MonsterBattle4.application.model.Type_com;


@Controller // Viewあり。Viewを返却するアノテーション
public class BattleController
{
	// Sessionの定義
	@Autowired
	HttpSession session; // セッション管理
	// DBと接続するやつの定義
	@Autowired
	JdbcTemplate jdbcTemplate;


	@RequestMapping(value="/MonsterBattle4",params="battle_type=battle_start", method = RequestMethod.POST) // URLとのマッピング
	public ModelAndView battle(ModelAndView mav, @Valid Form form)
	{
		int selectPoke_num = form.getPoke_num();

		// selectPoke_numを使って、SQLで対象のポケモンのステータスを取得する。
		List<DefaultPoke> selectlist = jdbcTemplate.query
				("SELECT * "
					+ "FROM m_defaultpokemon "
					+ "where poke_num = " + selectPoke_num
					, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));

		session.setAttribute("Mypokelist", selectlist.get(0));

		// ランダムで相手のポケモンを作成
		// 乱数を使って相手のpoke_numを決定した後、SQLで対象のポケモンのステータスを取得

		Random rndm = new Random();
		int enemynum = rndm.nextInt(8)+1;

		System.out.println("enemynum is " + enemynum);

		// selectPoke_numを使って、SQLで対象のポケモンのステータスを取得する。
		List<DefaultPoke> enemylist = jdbcTemplate.query
				("SELECT * "
					+ "FROM m_defaultpokemon "
					+ "where poke_num = " + enemynum
					, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));


		session.setAttribute("Enemylist", enemylist.get(0));



		DefaultPoke mypoke = (DefaultPoke)session.getAttribute("Mypokelist");
		DefaultPoke enemypoke = (DefaultPoke)session.getAttribute("Enemylist");

		// ログ
		List<String> log = new ArrayList<String>();


		// バトル前処理
		// ポケモンのステータスを取得
		int poke_num = mypoke.getPoke_num();
		String myname = mypoke.getPoke_name();
		int mytype_num1 = mypoke.getPoke_type1();
		String mytype_name1 = mypoke.getPoke_typename1();
		int mytype_num2 = mypoke.getPoke_type2();
		String mytype_name2 = mypoke.getPoke_typename2();
//		String rare = mypoke.getRare();
//		int nextpoke_num = mypoke.getNextpoke_num();
//		int evolution_lv = mypoke.getEvolution_lv();
		int myhp = (Integer)mypoke.getHP();
		int myattack = (Integer)mypoke.getAttack();
		int myspeed = (Integer)mypoke.getSpeed();

		int enemy_num = enemypoke.getPoke_num();
		String enemyname = enemypoke.getPoke_name();
		int enemytype_num1 = enemypoke.getPoke_type1();
		String enemytype_name1 = enemypoke.getPoke_typename1();
		int enemytype_num2 = enemypoke.getPoke_type2();
		String enemytype_name2 = enemypoke.getPoke_typename2();
		int enemyhp = enemypoke.getHP();
		int enemyattack = enemypoke.getAttack();
		int enemyspeed = enemypoke.getSpeed();


		// バトル前判定(タイプ)
		// 自分のタイプと相手のタイプの相性比較
		List<Type_com> typecomlist = jdbcTemplate.query
				("SELECT type_" + enemytype_num1
					+ " FROM m_type_com "
					+ "where type_num = " + mytype_num1
					, new BeanPropertyRowMapper<Type_com>(Type_com.class));
		// 倍率１
		double mybairitu1 = 1.0;
		try {
			Class<?> c = Class.forName("jp.co.systena.tigerscave.MonsterBattle4.application.model.Type_com");
			// 実行対象のオブジェクト
			Object object = typecomlist.get(0);

			// 実行メソッド名の定義
			String method_name = "getType_" + enemytype_num1;
			Method method = c.getMethod(method_name);
			// メソッドの実行
			String objStr = method.invoke(object).toString();
			mybairitu1 = new Double(objStr).doubleValue();

		}catch(ReflectiveOperationException e) {
			e.printStackTrace();
		}

		// 倍率2
		double mybairitu2 = 1.0;
		// 相手のタイプ2があったら
		if(enemytype_num2 != 0)
		{
			List<Type_com> typecomlist2 = jdbcTemplate.query
					("SELECT type_" + enemytype_num2
						+ " FROM m_type_com "
						+ "where type_num = " + mytype_num1
						, new BeanPropertyRowMapper<Type_com>(Type_com.class));

			try {
				Class<?> c = Class.forName("jp.co.systena.tigerscave.MonsterBattle4.application.model.Type_com");
				// 実行対象のオブジェクト
				Object object = typecomlist2.get(0);

				// 実行メソッド名の定義
				String method_name = "getType_" + enemytype_num2;
				Method method = c.getMethod(method_name);
				// メソッドの実行
				String objStr = method.invoke(object).toString();
				mybairitu2 = new Double(objStr).doubleValue();

			}catch(ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		else
		{
			 mybairitu2 = 1.0;
		}

		System.out.println("mybairitu1 is " + mybairitu1);
		System.out.println("mybairitu2 is " + mybairitu2);

		// 倍率を含んだ攻撃力
		myattack = (int) (myattack * mybairitu1 * mybairitu2);





		// 相手のタイプと自分のタイプの相性比較
		List<Type_com> typecomlist01 = jdbcTemplate.query
				("SELECT type_" + mytype_num1
					+ " FROM m_type_com "
					+ "where type_num = " + enemytype_num1
					, new BeanPropertyRowMapper<Type_com>(Type_com.class));
		// 倍率１
		double enemybairitu1 = 1.0;
		try {
			Class<?> c = Class.forName("jp.co.systena.tigerscave.MonsterBattle4.application.model.Type_com");
			// 実行対象のオブジェクト
			Object object = typecomlist01.get(0);

			// 実行メソッド名の定義
			String method_name = "getType_" + mytype_num1;
			Method method = c.getMethod(method_name);
			// メソッドの実行
			String objStr = method.invoke(object).toString();
			enemybairitu1 = new Double(objStr).doubleValue();

		}catch(ReflectiveOperationException e) {
			e.printStackTrace();
		}

		// 倍率2
		double enemybairitu2 = 1.0;
		// 相手のタイプ2があったら
		if(mytype_num2 != 0)
		{
			List<Type_com> typecomlist02 = jdbcTemplate.query
					("SELECT type_" + mytype_num2
						+ " FROM m_type_com "
						+ "where type_num = " + enemytype_num1
						, new BeanPropertyRowMapper<Type_com>(Type_com.class));

			try {
				Class<?> c = Class.forName("jp.co.systena.tigerscave.MonsterBattle4.application.model.Type_com");
				// 実行対象のオブジェクト
				Object object = typecomlist02.get(0);

				// 実行メソッド名の定義
				String method_name = "getType_" + mytype_num2;
				Method method = c.getMethod(method_name);
				// メソッドの実行
				String objStr = method.invoke(object).toString();
				enemybairitu2 = new Double(objStr).doubleValue();

			}catch(ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		else
		{
			 enemybairitu2 = 1.0;
		}

		System.out.println("enemybairitu1 is " + enemybairitu1);
		System.out.println("enemybairitu2 is " + enemybairitu2);

		// 倍率を含んだ攻撃力
		enemyattack = (int) (enemyattack * enemybairitu1 * enemybairitu2);

//バトルVS
		do
		{
			// 自分先攻のとき
			if(myspeed >= enemyspeed)
			{
				// 自分の攻撃
				// 敵にこうげきする

				enemyhp = enemyhp - myattack;
				enemypoke.setHP(enemyhp);

				log.add(myname + "　のこうげき");
				log.add("相手の　" + enemyname + "　に　" + myattack + "　ダメージ！");
				if(enemyhp >= 1)
				{
					log.add(" 相手の　" + enemyname +  "　は残りHP　" + enemyhp);
					log.add("   ");
				}
				mav.addObject("Log", log);

				// 敵のHPが無くなったとき
				if(enemyhp <= 0)
				{

					String message = "WIN！";
					mav.addObject("message", message);
					log.add("相手の　" + enemyname + "　は戦闘不能");
					mav.addObject("Log", log);
					break;
				}
				// 相手の攻撃
				else
				{
					// こうげきを受ける
					myhp = myhp - enemyattack;
					log.add("相手の　" + enemyname + "　のこうげき");
					log.add(myname + "　に　" + enemyattack + "　ダメージ！");
					if(myhp >= 1)
					{
						log.add(myname + "　は残りHP　" + myhp);
						log.add("   ");
					}
					mav.addObject("Log", log);

					if(myhp <= 0)
					{
						String message = "LOSE...";
						mav.addObject("message", message);
						log.add(myname + "　は戦闘不能");
						mav.addObject("Log", log);
						break;
					}
				}
			}

			// 自分が後攻のとき
			else if(myspeed < enemyspeed)
			{
				// こうげきを受ける
				myhp = myhp - enemyattack;
				log.add("相手の　" + enemyname + "　のこうげき");
				log.add(myname + "　に　" + enemyattack + "　ダメージ！");
				if(myhp >= 1)
				{
					log.add(myname + "　は残りHP　" + myhp);
					log.add("   ");
				}
				mav.addObject("Log", log);

				if(myhp <= 0)
				{
					String message = "LOSE...";
					mav.addObject("message", message);
					log.add(myname + "　は戦闘不能");
					mav.addObject("Log", log);
					break;
				}
				else
				{
										// 敵にこうげきする
					enemyhp = enemyhp - myattack;
					enemypoke.setHP(enemyhp);
					log.add(myname + "　のこうげき");
					log.add("相手の　" + enemyname + "　に　" + myattack + "　ダメージ！");
					if(enemyhp >= 1)
					{
						log.add("相手の　" + enemyname + "　は残りHP　" + enemyhp);
						log.add("   ");
					}
					mav.addObject("Log", log);

					// 敵のHPが無くなったとき
					if(enemyhp <= 0)
					{
						String message = "WIN！";
						mav.addObject("message", message);
						log.add("相手の　" + enemyname + "　は戦闘不能");
						mav.addObject("Log", log);
						break;
					}
				}
			}
		}
		while(myhp >= 1 || enemyhp >= 1);



		//SELECTを使用してテーブルの情報を取得する
	    List<DefaultPoke> partylist = jdbcTemplate.query
	    		("SELECT B.poke_name, B.poke_num, hp, attack, speed,poke_typename1,poke_typename2 "
	    			+ "FROM trainer A "
	    			+ "left join m_defaultpokemon B "
	    			+ "on A.poke_num = B.poke_num"
	    			+ " where A.trainer_ID = 1"
	    			, new BeanPropertyRowMapper<DefaultPoke>(DefaultPoke.class));
	    mav.addObject("Partylist" , partylist);

	    // selectで一番最初にヒットしたポケモンの情報をmodelにセット
	    mav.addObject("SelectType", partylist.get(0));

	    // htmlでの入力を受け取るクラスをインスタンス化
	    Form inputform = new Form();
	    mav.addObject("Form",inputform);

		mav.setViewName("BattleView");
		return mav;
	}
}

/*
		if(mytype.equals(enemyfuritype))
		{
			myattack = myattack * 2;
		}
		// 自分のタイプが敵の有利タイプと一致しているかどうか
		else if(mytype.equals(enemyyuuritype))
		{
			myattack = myattack / 2;
		}
		else
		{}

		// 敵のタイプが自分の不利タイプと一致しているか
		if(enemytype.equals(myfuritype))
		{
			enemyattack = enemyattack * 2;
		}

		// 敵のタイプが自分の有利タイプと一致しているか
		else if(enemytype.equals(myyuuritype))
		{
			enemyattack = enemyattack / 2;
		}
		else {}


		// バトル前判定
		//SelectField selectfield = (SelectField)session.getAttribute("BattleField");
		//String battle = selectfield.getField();
		if(mytype.equals(yuurifield))
		{
			myhp = myhp + 10;
		}
		else if(mytype.equals(furifield))
		{
			myhp = myhp - 10;
		}
		else {}

		if(enemytype.equals(yuurifield))
		{
			enemyhp = enemyhp + 10;
		}
		else if(enemytype.equals(furifield))
		{
			enemyhp = enemyhp - 10;
		}
		else {}

		// バトルVS
		do
		{
			// 自分先攻のとき
			if(myspeed >= enemyspeed)
			{
				// 自分の攻撃
				// 敵にこうげきする
				enemyhp = enemyhp - myattack;
				enemypoke.setHp(enemyhp);
				log.add(myname + "　のこうげき");
				log.add("相手の　" + enemyname + "　に　" + myattack + "　ダメージ！");
				if(enemyhp >= 1)
				{
					log.add(" 相手の　" + enemyname +  "　は残りHP　" + enemyhp);
					log.add("   ");
				}
				session.setAttribute("Log", log);

				// 敵のHPが無くなったとき
				if(enemyhp <= 0)
				{

					String message = "WIN！";
					session.setAttribute("message", message);
					log.add("相手の　" + enemyname + "　は戦闘不能");
					session.setAttribute("Log", log);
					break;
				}
				// 相手の攻撃
				else
				{
					// こうげきを受ける
					myhp = myhp - enemyattack;
					log.add("相手の　" + enemyname + "　のこうげき");
					log.add(myname + "　に　" + enemyattack + "　ダメージ！");
					if(myhp >= 1)
					{
						log.add(myname + "　は残りHP　" + myhp);
						log.add("   ");
					}
					session.setAttribute("Log", log);

					if(myhp <= 0)
					{
						String message = "LOSE...";
						session.setAttribute("message", message);
						log.add(myname + "　は戦闘不能");
						session.setAttribute("Log", log);
						break;
					}
				}
			}

			// 自分が後攻のとき
			else if(myspeed > enemyspeed)
			{
				// こうげきを受ける
				myhp = myhp - enemyattack;
				log.add("相手の　" + enemyname + "　のこうげき");
				log.add(myname + "　に　" + enemyattack + "　ダメージ！");
				if(myhp >= 1)
				{
					log.add(myname + "　は残りHP　" + myhp);
				}
				session.setAttribute("Log", log);

				if(myhp <= 0)
				{
					String message = "LOSE...";
					session.setAttribute("message", message);
					log.add(myname + "　は戦闘不能");
					session.setAttribute("Log", log);
					break;
				}
				else
				{
					// 敵にこうげきする
					enemyhp = enemyhp - myattack;
					enemypoke.setHp(enemyhp);
					log.add(myname + "　のこうげき");
					log.add("相手の　" + enemyname + "　に　" + myattack + "　ダメージ！");
					if(enemyhp >= 1)
					{
						log.add("相手の　" + enemyname + "　は残りHP　" + enemyhp);
					}
					session.setAttribute("Log", log);

					// 敵のHPが無くなったとき
					if(enemyhp <= 0)
					{
						String message = "WIN！";
						session.setAttribute("message", message);
						log.add("相手の　" + enemyname + "　は戦闘不能");
						session.setAttribute("Log", log);
						break;
					}
				}
			}
		}
		while(myhp >= 1 || enemyhp >= 1);

		return new ModelAndView("redirect:/MonsterBattle4");

	}
}




		// フィールドを設定
		Random rnd = new Random();
		int fieldnum = rnd.nextInt(3)+1;
		String yuurifield;
		String furifield;

		if(fieldnum == 1)
		{
			yuurifield = "草";
			furifield = "水";
			// 草のフィールドを作成
			SelectField grassfield = new SelectField(yuurifield,furifield);
			// セッションにフィールドをセット
			session.setAttribute("BattleField", grassfield);
			//System.out.println("fieldは：" + yuurifield);
		}
		else if (fieldnum == 2)
		{
			yuurifield = "炎";
			furifield = "草";
			// 炎のフィールドを作成
			SelectField firefield = new SelectField(yuurifield,furifield);
			// セッションにフィールドをセット
			session.setAttribute("BattleField", firefield);
			//System.out.println("fieldは：" + yuurifield);
		}
		else
		{
			yuurifield = "水";
			furifield = "炎";
			// 水のフィールドを作成
			SelectField waterfield = new SelectField(yuurifield,furifield);
			// セッションにフィールドをセット
			session.setAttribute("BattleField", waterfield);
			//System.out.println("fieldは：" + yuurifield);
		}
		log.add("バトルフィールド:　" + yuurifield);
		log.add("相手:　" + enemyname);
		log.add("   ");
*/