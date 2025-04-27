package cainongmod;

import cainongmod.cards.*;
import cainongmod.character.Mycharacter;
import cainongmod.enums.CardColorEnum;
import cainongmod.enums.LibraryTypeEnum;
import cainongmod.relics.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.helpers.CardColorBundle;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class cainongmod implements EditCardsSubscriber,
                                       PostInitializeSubscriber,
                                       EditStringsSubscriber,
                                       EditRelicsSubscriber,
                                       EditKeywordsSubscriber,EditCharactersSubscriber {
    public static final String MOD_ID = "cainongmod";
    public static final Color CAINONG_CARDCOLOR = new Color(1.0F, 102.0F / 255.0F, 102.0F / 178.0F, 1.0F);
    private static final String MY_CHARACTER_BUTTON = getResourcePath("char/Character_Button.png");

    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = getResourcePath("char/Character_Portrait.png");
    public static void initialize() {
        new cainongmod();
    }

    public cainongmod() {
        BaseMod.subscribe(this);
        CardColorBundle bundle = new CardColorBundle();
        bundle.cardColor = CAINONG_CARD;
        bundle.modId = MOD_ID;
        bundle.bgColor =
            bundle.cardBackColor =
            bundle.frameColor =
            bundle.frameOutlineColor =
            bundle.descBoxColor =
            bundle.trailVfxColor =
            bundle.glowColor = CAINONG_CARDCOLOR;
        bundle.libraryType = LibraryTypeEnum.CAINONG_CARD;
        bundle.attackBg = getResourcePath("512/bg_attack_512.png");
        bundle.skillBg = getResourcePath("512/bg_skill_512.png");
        bundle.powerBg = getResourcePath("512/bg_power_512.png");
        bundle.cardEnergyOrb = getResourcePath("char/card_orb.png");
        bundle.energyOrb = getResourcePath("char/small_orb.png");
        bundle.attackBgPortrait = getResourcePath("1024/bg_attack.png");
        bundle.skillBgPortrait = getResourcePath("1024/bg_skill.png");
        bundle.powerBgPortrait = getResourcePath("1024/bg_power.png");
        bundle.energyOrbPortrait = getResourcePath("1024/cost_orb.png");
        bundle.setEnergyPortraitWidth(164);
        bundle.setEnergyPortraitHeight(164);
        BaseMod.addColor(bundle);
    }

    public static String makeId(String name) {
        return MOD_ID + ":" + name;
    }

    public static String getResourcePath(String path) {
        return "CaiNongModResources/img/" + path;
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new PaiDianDian());
        BaseMod.addCard(new SuoKai());
        BaseMod.addCard(new CandyBall());
        BaseMod.addCard(new BideTime());
        BaseMod.addCard(new NongEye());
        BaseMod.addCard(new NongOfAllTrades());
        BaseMod.addCard(new AreYouTwoLoong());
        BaseMod.addCard(new Nongdial());
        BaseMod.addCard(new Jimi());
        BaseMod.addCard(new Nongserk());
        BaseMod.addCard(new RoastPig());
        BaseMod.addCard(new UncleOfChild());
        BaseMod.addCard(new LookForUncleOfChild());
        BaseMod.addCard(new DefendToKill());
        BaseMod.addCard(new AttackToKill());
        BaseMod.addCard(new WhirlingAroundLikeAClown());
        BaseMod.addCard(new UnmannedForm());
        BaseMod.addCard(new InstantKill());
        BaseMod.addCard(new DeckIsTooBig());
        BaseMod.addCard(new SealGuide());
        BaseMod.addCard(new JammedStart());
        BaseMod.addCard(new TangInfinite());
        BaseMod.addCard(new NongBarricade());
        BaseMod.addCard(new NongEntrench());
        BaseMod.addCard(new NongBox());
        BaseMod.addCard(new ROSL());
        BaseMod.addCard(new DirectTraffic());
        BaseMod.addCard(new AllTheSame());
        BaseMod.addCard(new DrainPigBlood());
        BaseMod.addCard(new DrainPigBlood());
        BaseMod.addCard(new JoeyWheelerTheSpire());
        BaseMod.addCard(new Protect());
        BaseMod.addCard(new Thirsty());
        BaseMod.addCard(new HuiShi());
        BaseMod.addCard(new MappingFunction());
        BaseMod.addCard(new ParentsMeeting());
        BaseMod.addCard(new TangCry());
        BaseMod.addCard(new TangOfThreeWaves());
        BaseMod.addCard(new FlameBarrier());
        BaseMod.addCard(new NongSearing());
        BaseMod.addCard(new NongWalk());
        BaseMod.addCard(new Belly());
        BaseMod.addCard(new Demand());
        BaseMod.addCard(new GoodSeed());
        BaseMod.addCard(new BadSeed());
        BaseMod.addCard(new DoNothing());
        BaseMod.addCard(new Epilepsy());
        BaseMod.addCard(new AllCorrect());
        BaseMod.addCard(new Ladybird());
        BaseMod.addCard(new DuckGuide());
        BaseMod.addCard(new IAmNotWilling());
        BaseMod.addCard(new CarnageRuinTheRedPants());
        BaseMod.addCard(new GarenMen());
        BaseMod.addCard(new TooSick());
        BaseMod.addCard(new HeadChase());
        BaseMod.addCard(new BugBlade());
        BaseMod.addCard(new OffItShrug());
        BaseMod.addCard(new WhiteLotusPuzzle());
        BaseMod.addCard(new WarMachine());
        BaseMod.addCard(new GoodTransitionCaltrops());
        BaseMod.addCard(new NongAbacus());
        BaseMod.addCard(new MuscleMemory());
        BaseMod.addCard(new SpireArtist());
        BaseMod.addCard(new NoseNib());
        BaseMod.addCard(new DeleteCard());
        BaseMod.addCard(new TangLaugh());
        BaseMod.addCard(new Storytelling());
        BaseMod.addCard(new FireRaid());
        BaseMod.addCard(new YouKnowWhyISay());
        BaseMod.addCard(new Snack());
        BaseMod.addCard(new TortureAudience());
        BaseMod.addCard(new Trance());
        BaseMod.addCard(new Statuette());
        BaseMod.addCard(new SLGod());
        BaseMod.addCard(new DisgustingAudience());
        BaseMod.addCard(new DeadBranch());
        BaseMod.addCard(new GetInside());
        BaseMod.addCard(new FunkYou());
        BaseMod.addCard(new Kawhi());
        BaseMod.addCard(new EnterTheStream());
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.getColorBundleMap().get(CAINONG_CARD).loadRegion();

    }

    @Override
    public void receiveEditStrings() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
            default:
                language = "eng";
        }
        BaseMod.loadCustomStringsFile(MOD_ID, CardStrings.class, "localization/" + language + "/cards.json");
        BaseMod.loadCustomStringsFile(MOD_ID, RelicStrings.class, "localization/" + language + "/relics.json");
        BaseMod.loadCustomStringsFile(MOD_ID, CharacterStrings.class, "localization/" + language + "/characters.json");
        BaseMod.loadCustomStringsFile(MOD_ID, EventStrings.class, "localization/" + language + "/events.json");
        BaseMod.loadCustomStringsFile(MOD_ID, MonsterStrings.class, "localization/" + language + "/monsters.json");
        BaseMod.loadCustomStringsFile(MOD_ID, PowerStrings.class, "localization/" + language + "/powers.json");
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new BoiledPeanut(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new PinkTShirt(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new NeowsEnjoying(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new TangPill(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new OldComputer(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new NaiLong(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new PensionFond(), CAINONG_CARD);
        BaseMod.addRelicToCustomPool(new Factory(), CAINONG_CARD);
    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword("cainongmod", "唐", new String[]{"唐"}, "回合开始时随机使用X张手牌(消耗能量), 使用后层数减1。");
        BaseMod.addKeyword("cainongmod", "农白", new String[]{"农白"}, "造成的伤害降低 #y25% 。");
        BaseMod.addKeyword("cainongmod", "农黑", new String[]{"农黑"}, "造成的伤害提高 #y25% , 可以叠加。");
        BaseMod.addKeyword("cainongmod", "弹幕", new String[]{"弹幕"}, "在手牌中时根据你上一张使用的其他牌的类型获得不同效果。 NL 攻击-辱骂 : NL 数值变为2倍 NL 技能-享受 : NL 直到打出前耗能变为0 NL 能力-独轮车 : NL 打出后回到手牌");
        BaseMod.addKeyword("cainongmod", "点播", new String[]{"点播"}, "选择以下点播之一: NL 在3回合内结束战斗 NL 在7回合后结束战斗 NL 空过1回合 NL 在1回合中打出7张牌 NL #b农白苞米私募了");
        BaseMod.addKeyword("cainongmod", "摆烂", new String[]{"摆烂"}, "-50(农白应得的)");
        BaseMod.addKeyword("", "火焰吐息", new String[]{"火焰吐息"}, "抽到 状态 或 诅咒 牌时对所有敌人造成 X 点伤害 。");
        BaseMod.addKeyword("", "发现", new String[]{"发现"}, "从三张牌种选择一张加入手牌。");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Mycharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, CardColorEnum.Cangjie);

    }
}
