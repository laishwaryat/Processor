package processor.memorysystem;

import generic.Element;
import generic.Event;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;
import generic.Event.EventType;
import generic.ExecutionCompleteEvent;
import processor.Clock;

public class MainMemory implements Element{
	 public int[] memory;
	
	public MainMemory()
	{
		memory = new int[65536];
	}
	
	public int getWord(int address)
	{
		return memory[address];
	}
	
	public void setWord(int address, int value)
	{
		memory[address] = value;
	}
	
	public String getContentsAsString(int startingAddress, int endingAddress)
	{
		if(startingAddress == endingAddress)
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nMain Memory Contents:\n\n");
		for(int i = startingAddress; i <= endingAddress; i++)
		{
			sb.append(i + "\t\t: " + memory[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	@Override
	public void handleEvent(Event e) {
		if(e .getEventType()==EventType.MemoryRead)
		{
		MemoryReadEvent event = (MemoryReadEvent) e ;
		Simulator.getEventQueue().addEvent (
		new MemoryResponseEvent (
		Clock.getCurrentTime() ,
		this ,
		event.getRequestingElement() ,
		getWord(event.getAddressToReadFrom()))) ;
		}	
		
		if(e.getEventType()==EventType.MemoryWrite)
		{
			MemoryWriteEvent event = (MemoryWriteEvent) e ;
			int addr=event.getAddressToWriteTo();
			int val=event.getValue();
			setWord(addr,val);
		}
		if(e.getEventType()==EventType.ExecutionComplete) {
			ExecutionCompleteEvent event=(ExecutionCompleteEvent) e;
			
		}
		
	}
}
