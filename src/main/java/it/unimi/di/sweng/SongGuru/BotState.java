package it.unimi.di.sweng.SongGuru;

public interface BotState {

	public BotState nextState(final String msg);

}
