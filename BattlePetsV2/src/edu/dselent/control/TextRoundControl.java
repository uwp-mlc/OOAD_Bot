package edu.dselent.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.dselent.config.ApplicationConfigurations;
import edu.dselent.damage.Calculatable;
import edu.dselent.damage.Damage;
import edu.dselent.damage.DamageCalculator;
import edu.dselent.domain.PlayerRoundData;
import edu.dselent.domain.RngHolder;
import edu.dselent.domain.Round;
import edu.dselent.event.AttackEvent;
import edu.dselent.event.AttackEventShootTheMoon;
import edu.dselent.event.RoundStartEvent;
import edu.dselent.io.IoManager;
import edu.dselent.player.PetTypes;
import edu.dselent.player.Playable;
import edu.dselent.skill.Skill;
import edu.dselent.skill.SkillRegistry;
import edu.dselent.skill.Skills;
import edu.dselent.skill.skilldata.ShootTheMoonData;
import edu.dselent.skill.skilldata.SkillData;
//import edu.dselent.unused.OutputObject;

public class TextRoundControl
{
	private TextFightControl textFightControl;
	private Calculatable damageCalculator;
	private Round currentRound;
	
	TextRoundControl(TextFightControl textFightControl)
	{
		this.textFightControl = textFightControl;
		this.damageCalculator = new DamageCalculator(this);
	}
	
	void runRound(Round round)
	{
		//Scanner s = new Scanner(System.in);
		//s.nextLine();

        // For debugging
		///OutputObject outputObject = new OutputObject();

		List<Playable> playableList = round.getFight().getBattle().getPlayableList();
		
		IoManager ioManager = ApplicationConfigurations.INSTANCE.getIoManager();
		ioManager.getOutputSender().outputString("Round " + (round.getRoundNumber()+1) + " Started");
		ioManager.getOutputSender().outputString("\n");
		ioManager.getOutputSender().outputString("Pets");
		ioManager.getOutputSender().outputString("\n");
		
		for(int i=0; i<playableList.size(); i++)
		{
			Playable currentPlayer = playableList.get(i);
			ioManager.getOutputSender().outputString("Pet " + (i+1));
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Current HP: " + currentPlayer.getCurrentHp());
			ioManager.getOutputSender().outputString("\n");

			//outputObject.addHp(currentPlayer.getCurrentHp());

			// TODO output skill set
		}

		ioManager.getOutputSender().outputString("Sorted by HP");
		ioManager.getOutputSender().outputString("\n");
		
		List<Playable> sortedPlayableList = new ArrayList<>(playableList);
		sortedPlayableList.sort((p1, p2) -> Double.compare(p2.getCurrentHp(), p1.getCurrentHp()));
		
		for(int i=0; i<sortedPlayableList.size(); i++)
		{
			Playable currentPlayer = sortedPlayableList.get(i);
			ioManager.getOutputSender().outputString("Pet " + (i+1));
			ioManager.getOutputSender().outputString("Pet Name: " + currentPlayer.getPetName());
			ioManager.getOutputSender().outputString("Pet Type: " + currentPlayer.getPetType());
			ioManager.getOutputSender().outputString("Current HP: " + currentPlayer.getCurrentHp());
			ioManager.getOutputSender().outputString("\n");
			
			// TODO output skill set
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		EventBus eventBus = textFightControl.getTextBattleControl().getEventBus();

		RoundStartEvent roundStartEvent = new RoundStartEvent(round.getRoundNumber());
		eventBus.fireEvent(roundStartEvent);

		currentRound = round;
		
		// Initialize round data
		for(int i=0; i<playableList.size(); i++)
		{
			round.getPlayerRoundDataList().add(new PlayerRoundData());
		}
		
		// Choose skill and prediction
		for(int i=0; i<playableList.size(); i++)
		{
			Playable player = playableList.get(i);
			
			// TODO what was this for again?
			// ^I use these to store skill choices so I only call from the playable once
			// ^Also used for information storage to keep track of the game history
			// ^Also helpful for applying damage when pets fall asleep
			SkillData skillData;
			boolean sleeping = false;
			
			if(player.isAwake())
			{
				Skills skillChoice = player.chooseSkill();
				
				if(skillChoice == Skills.SHOOT_THE_MOON)
				{
					Skills skillPrediction = player.getSkillPrediction();
					skillData = new ShootTheMoonData(skillChoice, skillPrediction);
				}
				else
				{
					skillData = new SkillData(skillChoice);
				}
			}
			else
			{
				skillData = new SkillData(null);
				sleeping = true;
			}
			
			round.getPlayerRoundDataList().get(i).setSkillData(skillData);
			round.getPlayerRoundDataList().get(i).setSleeping(sleeping);
		}
		
		// Calculate damage done
		for(int i=0; i<playableList.size(); i++)
		{
			Playable player = playableList.get(i);
			
			if(player.isAwake())
			{
				int victimPlayerIndex = findVictimPlayerIndex(i);
				Damage currentDamage = damageCalculator.calculateDamage(i, victimPlayerIndex);

				// TODO
                // Need to make some modifications for sleep scent about around here

				PlayerRoundData playerRoundData = round.getPlayerRoundDataList().get(i);
				playerRoundData.setDamageDone(currentDamage);
				playerRoundData.setVictimPlayerIndex(victimPlayerIndex);
				
			}
		}

		// Decrement skill recharge times
		// Set skill recharge time
		// Apply damage

		for (Playable player : playableList)
		{
			if (player.isAwake())
			{
				player.decrementRechargeTimes();
			}
		}
		
		for(int i=0; i<playableList.size(); i++)
		{
			Playable player = playableList.get(i);
			
			if(player.isAwake())
			{
				PlayerRoundData playerRoundData = round.getPlayerRoundDataList().get(i);
				Skills chosenSkill = playerRoundData.getSkillData().getSkill();
				
				//TODO improve
				List<Skill> skillList = SkillRegistry.INSTANCE.getSkillList();
				int rechargeTime = -1;
				
				for(Skill skill : skillList)
				{
					if(skill.getSkillEnum() == chosenSkill)
					{
						rechargeTime = skill.getRechargeTime();
					}
				}
				//
				
				player.setRechargeTime(chosenSkill, rechargeTime);
			}
		}
		
		// for each player
			// map for given
			// map for taken
		
		Map<Integer, Double> randomTakenMap = new HashMap<>();
		Map<Integer, Double> randomGivenMap = new HashMap<>();
	
		// need random damage difference for this turn
		// fight.updateRandomDamageDifference
		
		List<PlayerRoundData> playerRoundDataList = round.getPlayerRoundDataList();
		for(int attackingPlayableIndex=0; attackingPlayableIndex<playerRoundDataList.size(); attackingPlayableIndex++)
		{
			PlayerRoundData playerRoundData = playerRoundDataList.get(attackingPlayableIndex);

			if(!playerRoundData.isSleeping())
			{
				SkillData skillData = playerRoundData.getSkillData();
				Skills skillChoice = skillData.getSkill();
				Skills predictedSkillEnum = null;
				
				if(skillData instanceof ShootTheMoonData)
				{
					ShootTheMoonData stmData = (ShootTheMoonData)skillData;
					predictedSkillEnum = stmData.getPredictedSkillEnum();
				}
								
				Damage damage = playerRoundData.getDamageDone();
				int victimPlayerIndex = playerRoundData.getVictimPlayerIndex();
				
				randomGivenMap.put(attackingPlayableIndex, damage.getRandomDamage());
				randomTakenMap.put(victimPlayerIndex, damage.getRandomDamage());
			
				Playable victimPlayer = playableList.get(victimPlayerIndex);
				victimPlayer.updateHp(damage.calculateTotalDamage());

				AttackEvent attackEvent;

				if(skillChoice == Skills.SHOOT_THE_MOON)
				{
					AttackEventShootTheMoon.AttackEventShootTheMoonBuilder stmBuilder = new AttackEventShootTheMoon.AttackEventShootTheMoonBuilder();
					attackEvent = stmBuilder.withAttackingPlayableUid(attackingPlayableIndex)
							.withVictimPlayableUid(victimPlayerIndex)
							.withAttackingSkillChoice(skillChoice)
							.withDamage(damage)
							.withPredictedSkillEnum(predictedSkillEnum)
							.build();
				}
				else
				{

					attackEvent = new AttackEvent(attackingPlayableIndex, victimPlayerIndex, skillChoice, damage);
				}
				
				eventBus.fireEvent(attackEvent);
				
				StringBuilder sb = new StringBuilder();
				sb.append(playableList.get(attackingPlayableIndex).getPetName());
				sb.append(" Uses ");
				sb.append(skillChoice.toString());
				
				if(skillChoice == Skills.SHOOT_THE_MOON)
				{
					sb.append(" with a prediction of ");
					sb.append(predictedSkillEnum);
				}
				
				sb.append(" and does ");
				sb.append(damage.getRandomDamage());
				sb.append(" random damage and ");
				sb.append(damage.getConditionalDamage());
				sb.append(" conditional damage to ");
				sb.append(playableList.get(victimPlayerIndex).getPetName());


				//outputObject.addBalance(round.getFight().getRandomDamageDifference(attackingPlayableIndex));
				//outputObject.addSkill(skillChoice);
				//outputObject.addRandomDamage(damage.getRandomDamage());
				//outputObject.addConditionalDamage(damage.getConditionalDamage());


				ioManager.getOutputSender().outputString(sb.toString());
			}
		}
		
		// for each player
			// map for given
			// map for taken
		
			// need random damage difference for this turn
			// fight.updateRandomDamageDifference
		
		for(int i=0; i<playerRoundDataList.size(); i++)
		{			
			PlayerRoundData playerRoundData = playerRoundDataList.get(i);
			
			
			if(!playerRoundData.isSleeping())
			{
				double given = randomGivenMap.get(i);
				double taken = randomTakenMap.get(i);
				double difference = given - taken;
				round.getFight().updateRandomDamageDifference(i, difference);
			}
		}
		
		ioManager.getOutputSender().outputString("\n");

		// Useful for debugging
		//System.out.println(outputObject);
	}

	public RngHolder getRngHolder()
	{
		return textFightControl.getRngHolder();
	}
	
	public Skills getPlayerSkill(int playerIndex)
	{
		return currentRound.getPlayerRoundDataList().get(playerIndex).getSkillData().getSkill();
	}
	
	public Skills getPredictedSkillEnum(int playerIndex)
	{
		Skills predictedSkill = null;
		
		SkillData skillData = currentRound.getPlayerRoundDataList().get(playerIndex).getSkillData();
		
		try
		{
			if(skillData instanceof ShootTheMoonData)
			{
				ShootTheMoonData stmData = (ShootTheMoonData)skillData;
				predictedSkill = stmData.getPredictedSkillEnum();
			}
		}
		catch(Exception e)
		{
			// TODO make custom exception for failed cast
			e.printStackTrace();
		}
		
		return predictedSkill;
	}
	
	private int findVictimPlayerIndex(int attackingPlayerIndex)
	{
		int victimPlayerIndex = -1;
		boolean found = false;
		List<Playable> playableList = currentRound.getFight().getBattle().getPlayableList();
		
		int searchIndex = attackingPlayerIndex + 1;
		searchIndex = searchIndex % playableList.size();
		
		while(searchIndex != attackingPlayerIndex && !found)
		{
			Playable player = playableList.get(searchIndex);
			
			if(player.isAwake())
			{
				victimPlayerIndex = searchIndex;
				found = true;
			}
			
			searchIndex++;
			searchIndex = searchIndex % playableList.size();
		}
		
		return victimPlayerIndex;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public PetTypes getPetType(int playerIndex)
	{
		return currentRound.getFight().getBattle().getPlayableList().get(playerIndex).getPetType();
	}

	public double getCumulativeRandomDamageDifference(int playerIndex)
	{
		return currentRound.getFight().getRandomDamageDifference(playerIndex);
	}

	public double getPlayableHp(int playerIndex)
	{
		List<Playable> playableList = currentRound.getFight().getBattle().getPlayableList();
		return playableList.get(playerIndex).getCurrentHp();
	}
	
	public double getPlayableStartingHp(int playerIndex)
	{
		List<Playable> playableList = currentRound.getFight().getBattle().getPlayableList();
		return playableList.get(playerIndex).getStartingHp();
	}
	
	public boolean isSkillRecharging(int playerIndex, Skills skill)
	{
		List<Playable> playableList = currentRound.getFight().getBattle().getPlayableList();
		Playable playable = playableList.get(playerIndex);
		int rechargeTime = playable.getSkillRechargeTime(skill);
		
		return rechargeTime>0;
	}
}
