package edu.dselent.player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import neat.helpers.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import edu.dselent.damage.Damage;
import edu.dselent.event.AttackEvent;
import edu.dselent.event.FightStartEvent;
import edu.dselent.event.PlayerEventInfo;
import edu.dselent.event.RoundStartEvent;
import edu.dselent.settings.PlayerInfo;
import edu.dselent.skill.Skills;
import jNeatCommon.IOseq;
import jneat.Genome;
import jneat.NNode;
import jneat.Network;
import jneat.Organism;
import jneat.Population;

/**
 * @author Machine Learning Club
 *
 */
public class MLC_Instance extends PetInstance {
	private final Skills[] outputOrder = {Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, Skills.ROCK_THROW,
            Skills.SCISSORS_POKE, Skills.SHOOT_THE_MOON,Skills.PAPER_CUT, Skills.REVERSAL_OF_FORTUNE, 
            Skills.ROCK_THROW, Skills.SCISSORS_POKE};
	private Organism org;
	private Network net;
	
	protected Map<Skills, Integer> rechargingOpponentSkills;//, jarvisRechargingSkills;
	private int roundNumber, fightNumber, opponentIndex, jarvisIndex;
	protected int opponentUID;
	private String opponentName;
	protected PetTypes opponentType;
	protected Map<Integer, Double> randomDifference;
	protected double opponentHealth;
	private Random random;
	protected int roundsSinceUse;
	private double maxROFDiff;
	private Skills predictedSkill;//Override from the Player... We want to use this one. 
	protected Skills lastAttackSkill, lastOpponentAttackSkill;
	protected double lastConditionalDamage = 0.0;
	
	public MLC_Instance(int playableUid, PlayerInfo playerInfo, Organism org) {
		super(playableUid, playerInfo);
		this.org = org;
		//loadNetwork();
		//IOseq io = new IOseq("C:\\Users\\nickl\\Documents\\OOAD_Bot\\BattlePetsV2");
		Population p = new Population("C:\\Users\\nickl\\Documents\\OOAD_Bot\\BattlePetsV2");
		
		this.randomDifference = new HashMap<Integer, Double>();
		this.randomDifference.put(super.getPlayableUid(), 0.0);
		
		this.rechargingOpponentSkills = new HashMap<Skills, Integer>();
		this.rechargingOpponentSkills.put(Skills.ROCK_THROW, 0);
		this.rechargingOpponentSkills.put(Skills.SCISSORS_POKE, 0);
		this.rechargingOpponentSkills.put(Skills.PAPER_CUT, 0);
		this.rechargingOpponentSkills.put(Skills.SHOOT_THE_MOON, 0);
		this.rechargingOpponentSkills.put(Skills.REVERSAL_OF_FORTUNE, 0);
		
		this.random  = new Random(); //10320l
		roundsSinceUse = 0;
		maxROFDiff = (-1) * Double.MAX_VALUE;
	}
	
	public MLC_Instance(int playableUid, PlayerInfo playerInfo) {
		super(playableUid, playerInfo);
		//this.org = org;
		loadNetwork();
		//IOseq io = new IOseq("C:\\Users\\nickl\\Documents\\OOAD_Bot\\BattlePetsV2");
		//Population p = new Population("C:\\Users\\nickl\\Documents\\OOAD_Bot\\BattlePetsV2");
		
		this.randomDifference = new HashMap<Integer, Double>();
		this.randomDifference.put(super.getPlayableUid(), 0.0);
		
		this.rechargingOpponentSkills = new HashMap<Skills, Integer>();
		this.rechargingOpponentSkills.put(Skills.ROCK_THROW, 0);
		this.rechargingOpponentSkills.put(Skills.SCISSORS_POKE, 0);
		this.rechargingOpponentSkills.put(Skills.PAPER_CUT, 0);
		this.rechargingOpponentSkills.put(Skills.SHOOT_THE_MOON, 0);
		this.rechargingOpponentSkills.put(Skills.REVERSAL_OF_FORTUNE, 0);
		
		this.random  = new Random(); //10320l
		roundsSinceUse = 0;
		maxROFDiff = (-1) * Double.MAX_VALUE;
	}
	
	@Override
	public Skills chooseSkill() {
		List<Double> input = new ArrayList<Double>();
		
		Map<Skills, Integer> rechargeTimes = new HashMap<Skills, Integer>();
		for(Skills skill : this.outputOrder) {
			rechargeTimes.put(skill, super.getSkillRechargeTime(skill));
		}
		
		input.addAll(Helpers.generateOneHot(opponentType, PetTypes.values().length));
		input.addAll(Helpers.generateOneHot(getPlayerType(), PetTypes.values().length));
		input.addAll(Helpers.boundedRechargeTime(rechargeTimes));
		input.addAll(Helpers.boundedRechargeTime(rechargingOpponentSkills));
		input.addAll(Helpers.generateOneHot(lastAttackSkill, Skills.values().length));
		input.addAll(Helpers.generateOneHot(lastOpponentAttackSkill, Skills.values().length));
		input.add(super.calculateHpPercent());
		input.add(lastConditionalDamage != 0 ? 1.0 : 0.0);
		
		//System.out.println("Input array: " + input.size());
		
		return this.getOuputChoice(input);
		
		//return super.learnSkill();
	}
	
	private Skills getOuputChoice(List<Double> input) {
		Network brain = this.net;
		
		input.add(-1.0); // Add bias to the input
		
		double[] arrInputs = input.stream().mapToDouble(d -> d).toArray();
		
		brain.load_sensors(arrInputs);
		
		int maxNetDepth = brain.max_depth();
		
		for(int relax = 0; relax <= maxNetDepth; relax++) {
			brain.activate();
		}
		
		List<Double> outputs = new ArrayList<Double>();

		for(Object n : brain.getOutputs()) {
			NNode node = (NNode) n;
			outputs.add(node.getActivation());
		}
		
		//System.out.println(outputs);
		Skills chosen = this.getBestSkill(outputs);
		//System.out.println(super.rechargingSkills.keySet() + " : " + super.rechargingSkills.values());
		//System.out.println("AJ Chose: " + chosen.toString());
		return chosen;
	}
	
	private Skills getBestSkill(List<Double> outputs) {
		Map<Skills, Integer> rechargeTimes = new HashMap<Skills, Integer>();
		for(Skills skill : this.outputOrder) {
			rechargeTimes.put(skill, super.getSkillRechargeTime(skill));
		}
		
		int startIndex = 0;
		Skills skill = this.outputOrder[startIndex];
		while(startIndex < this.outputOrder.length && rechargeTimes.get(skill) != 0) {
			startIndex++;
			skill = this.outputOrder[startIndex];
		}
		
		int maxIndex = startIndex;
		
		for(int i = startIndex + 1; i < 4; i++) {
			skill = this.outputOrder[i];
			if(rechargeTimes.get(skill) == 0) {
				if(outputs.get(maxIndex) < outputs.get(i))
					maxIndex = i;
			}
		}
		
		return this.outputOrder[maxIndex];
	}
	
	private void loadNetwork() {
		Network net = null; 
		  
        // Deserialization 
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream("C:\\Users\\nickl\\Documents\\OOAD_Bot\\BattlePetsV2\\saved_net_final2.ser"); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            net = (Network)in.readObject(); 
            
            in.close(); 
            file.close();
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
        
        this.net = net;
	}
	
	/**
	 * Record everything in the fight start event that is needed 
	 * @param event
	 */
	protected void rememberFightStartEvent(FightStartEvent event) {
		//this.fightNumber = event.getFightNumber();
		List<PlayerEventInfo> playerEventInfo = event.getPlayerEventInfoList();
		this.rechargingOpponentSkills.forEach((key,value) -> value = 0);
		
		//Get Opponent information (index and name)
		for(int i = 0; i < playerEventInfo.size(); i++)
		{
			PlayerEventInfo playerInfo = playerEventInfo.get(i);
			this.randomDifference.put(i, 0.0);
			if(playerInfo.getPetName().equals(super.getPetName()))
			{
				this.jarvisIndex = i;
				this.opponentIndex = (i + 1) % playerEventInfo.size();
				int opponentUid = playerEventInfo.get(opponentIndex).getPlayableUid();
				this.opponentHealth = playerEventInfo.get((i+1) % playerEventInfo.size()).getStartingHp();
				this.opponentName = playerEventInfo.get(this.opponentIndex).getPetName();
				this.opponentType = playerEventInfo.get(this.opponentIndex).getPetType();
				this.randomDifference.put(super.getPlayableUid(), 0.0);
				this.randomDifference.put(opponentUid, 0.0);
			}
		}
	}

	/**
	 * Record all information from a round start event. 
	 * @param event
	 */
	protected void rememberRoundStartEvent(RoundStartEvent event){
		this.roundNumber = event.getRoundNumber();
	}
	
	/*
	 * Record all information from an attack event. 
	 */
	protected void rememberAttackEvent(AttackEvent event)
	{
		if(this.randomDifference.get(event.getAttackingPlayableUid()) == null) {
			this.randomDifference.put(event.getAttackingPlayableUid(), 0.0);
		}
		
		if(this.randomDifference.get(event.getVictimPlayableUid()) == null) {
			this.randomDifference.put(event.getVictimPlayableUid(), 0.0);
		}
		
		if(event.getAttackingPlayableUid() == super.getPlayableUid())
		{
			Damage d = event.getDamage();
			
			int rechargeTime = (event.getAttackingSkillChoice() == Skills.REVERSAL_OF_FORTUNE || event.getAttackingSkillChoice() == Skills.SHOOT_THE_MOON) ? 6 : 1;
			this.decrementOpponentRechargeTimes();
			this.rechargingOpponentSkills.put(event.getAttackingSkillChoice(), rechargeTime);
			
			this.randomDifference.put(event.getAttackingPlayableUid(), this.randomDifference.get(event.getAttackingPlayableUid()) - d.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayableUid(), this.randomDifference.get(event.getVictimPlayableUid()) + d.getRandomDamage());
			this.roundsSinceUse++;
			
			this.lastOpponentAttackSkill = event.getAttackingSkillChoice();
		}
		else if(event.getAttackingPlayableUid() == this.opponentUID)
		{
			Damage d = event.getDamage();
			
			this.opponentHealth -= (d.getConditionalDamage() + d.getRandomDamage());
			
			this.randomDifference.put(event.getAttackingPlayableUid(), this.randomDifference.get(event.getAttackingPlayableUid()) - d.getRandomDamage());
			this.randomDifference.put(event.getVictimPlayableUid(), this.randomDifference.get(event.getVictimPlayableUid()) + d.getRandomDamage());
		
			Skills s = event.getAttackingSkillChoice();
			this.lastAttackSkill = event.getAttackingSkillChoice();
			this.lastConditionalDamage = event.getDamage().getConditionalDamage();
		}
	}
	
	/**
	 * Decrement all opponent recharge times. 
	 */
	protected void decrementOpponentRechargeTimes()
	{
		for (Map.Entry<Skills, Integer> pair : rechargingOpponentSkills.entrySet())
		{
			int val = pair.getValue();
			Skills s = pair.getKey();
			
			if (val > 0)
			{
				rechargingOpponentSkills.put(s, --val);
			}
		}
	}

	@Override
	public void update(Object event) {
		// TODO Auto-generated method stub
		if(event instanceof FightStartEvent)
		{
			FightStartEvent fse = (FightStartEvent) event;
			rememberFightStartEvent(fse);
		}
		if(event instanceof RoundStartEvent)
		{
			RoundStartEvent rse = (RoundStartEvent) event;
			rememberRoundStartEvent(rse);
		}         
		if(event instanceof AttackEvent)
		{
			AttackEvent ae = (AttackEvent) event;
			this.rememberAttackEvent(ae);
		}
	}
}