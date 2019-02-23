package jp.co.systena.tigerscave.MonsterBattle4.application.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.systena.tigerscave.MonsterBattle4.application.model.CharacterForm;
import jp.co.systena.tigerscave.MonsterBattle4.application.model.SelectType;

@Controller // Viewあり。Viewを返却するアノテーション
public class CharacterController
{
	// Sessionの定義
		@Autowired
		HttpSession session; // セッション管理

		String name;
		String mytype;
		int hp;
		int attack;
		int speed;
		String yuuritype;
		String furitype;
		String selectpoke;

		//@RequestMapping("/MonsterBattle4") // URLとのマッピング
		@RequestMapping(value="/MonsterBattle5", method = RequestMethod.POST) // URLとのマッピング
		public ModelAndView show(ModelAndView mav)
		{
			// htmlでの入力を受け取るクラスをインスタンス化
			CharacterForm characterform = new CharacterForm();
			mav.addObject("characterForm",characterform);

			String selectnum = (String)session.getAttribute("selectnum");

			if(selectnum == null || selectnum.equals("Fushi"))
			{
				name = "フシギダネ";
				mytype = "草";
				hp = 130;
				attack = 7;
				speed = 53;
				yuuritype =  "水";
				furitype = "炎";
				selectpoke = "1";

				// フシギダネを作成
				SelectType fushigidane = new SelectType(name , mytype, hp , attack , speed , yuuritype  , furitype);

				mav.addObject("SelectType",fushigidane);
				session.setAttribute("SelectType", fushigidane);
			}
			else if( selectnum.equals("Hito") )
			{
				name = "ヒトカゲ";
				mytype = "炎";
				hp = 100;
				attack = 15;
				speed = 75;
				yuuritype =  "草";
				furitype = "水";
				selectpoke = "2";

				// ヒトカゲを作成
				SelectType Hitokage = new SelectType(name , mytype, hp , attack , speed , yuuritype  , furitype);

				mav.addObject("SelectType",Hitokage);
				session.setAttribute("SelectType", Hitokage);
			}
			else
			{
				name = "ゼニガメ";
				mytype = "水";
				hp = 80;
				attack = 10;
				speed = 100;
				yuuritype =  "炎";
				furitype = "草";
				selectpoke = "3";

				// ゼニガメを作成
				SelectType zenigame = new SelectType(name , mytype, hp , attack , speed , yuuritype  , furitype);
				mav.addObject("SelectType",zenigame);
				session.setAttribute("SelectType", zenigame);
			}

			// モデルに選択したポケモンのナンバーをセット
			mav.addObject("SelectPoke",selectpoke);

			// セッションに選択したポケモンのナンバーをセット
			session.setAttribute("SelectPoke", selectpoke);


			// 相手のポケモンを作成
			Random rnd = new Random();
			int enemynum = rnd.nextInt(3)+1;
			String enemyname;
			String enemytype;
			int enemyhp;
			int enemyattack;
			int enemyspeed;
			String enemyyuuritype;
			String enemyfuritype;

			if(enemynum == 1)
			{
				enemyname = "フシギダネ";
				enemytype = "草";
				enemyhp = 130;
				enemyattack = 7;
				enemyspeed = 53;
				enemyyuuritype =  "水";
				enemyfuritype = "炎";

				// フシギダネを作成
				SelectType enemyfushigidane = new SelectType(enemyname , enemytype ,  enemyhp , enemyattack , enemyspeed , enemyyuuritype  , enemyfuritype);

				// セッションに相手のポケモンをセット
				session.setAttribute("EnemyPoke", enemyfushigidane);

			}
			else if (enemynum == 2)
			{
				enemyname = "ヒトカゲ";
				enemytype = "炎";
				enemyhp = 100;
				enemyattack = 15;
				enemyspeed = 75;
				enemyyuuritype =  "草";
				enemyfuritype = "水";

				// ヒトカゲを作成
				SelectType enemyHitokage = new SelectType(enemyname , enemytype, enemyhp , enemyattack , enemyspeed , enemyyuuritype  , enemyfuritype);

				// セッションに相手のポケモンをセット
				session.setAttribute("EnemyPoke", enemyHitokage);
			}
			else
			{
				enemyname = "ゼニガメ";
				enemytype = "水";
				enemyhp = 80;
				enemyattack = 10;
				enemyspeed = 100;
				enemyyuuritype =  "炎";
				enemyfuritype = "草";

				// ゼニガメを作成
				SelectType enemyzenigame = new SelectType(enemyname , enemytype, enemyhp , enemyattack , enemyspeed , enemyyuuritype  , enemyfuritype);

				// セッションに相手のポケモンをセット
				session.setAttribute("EnemyPoke", enemyzenigame);
			}


			// テンプレート名をセット
			mav.setViewName("BattleView");
			return mav;
		}

		// sessionに入れる
		@RequestMapping(value="/MonsterBattle4", method = RequestMethod.POST)  // URLとのマッピング
		public ModelAndView order(ModelAndView mav, @Valid CharacterForm characterform, BindingResult bindingResult, HttpServletRequest request)
		{
			// 入力からポケモン情報を取得する
			String num = characterform.getNum();
			session.setAttribute("selectnum" , num);

			// MonsterBattle3にリダイレクト
			return new ModelAndView("redirect:/MonsterBattle4");
		}
}
