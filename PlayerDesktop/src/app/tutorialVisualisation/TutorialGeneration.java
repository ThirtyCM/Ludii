package app.tutorialVisualisation;

import java.io.File;
import java.util.Map;

import app.DesktopApp;
import app.PlayerApp;
import app.display.util.DesktopGUIUtil;
import app.loading.GameLoading;
import app.loading.TrialLoading;
import app.views.tools.ToolView;
import manager.Manager;
import other.move.Move;

public class TutorialGeneration
{

	private static String ludDirectory = "../Common/res/lud/";

	private static void wait(final int time) {
		try
		{
			Thread.sleep(time);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create an image of a specific move
	 * @param move the specific move to create an image from
	 */
	public static void createMoveImage(final PlayerApp app, final int move, final int i, final String suffix) 
	{
		// Turn on the arrows that show legal moves for the current piece
		app.bridge().settingsVC().setShowPossibleMoves(false);
		app.settingsPlayer().setShowLastMove(false);
		app.settingsPlayer().setTutorialVisualisationMoveType(suffix);
		
		DesktopApp.frame().setSize(500, 570);

		wait(1000);
		
		// Jump to the important move
		if (app.manager().settingsNetwork().getActiveGameId() == 0)
		{
			ToolView.jumpToMove(app, move);
		}
		
		wait(1000);
		
		// Create a screenshot
		DesktopGUIUtil.gameScreenshot("tutorialVisualisation/image" + i + "-" + suffix);
	}

	public static void main(final String[] args) 
	{
		final String gamePath = ludDirectory+LudGame.Breakthrough;
		
		// Start the UI
		final DesktopApp app = new DesktopApp();
		app.createDesktopApp();
		app.settingsPlayer().setPerformingTutorialVisualisation(true);
		GameLoading.loadGameFromFilePath(app, gamePath);
		final Manager manager = app.manager();

		final MoveChooser mc = new MoveChooser(gamePath);

		File trialFile = null;

		int fileCount = 0;
		// Get the most important move
		if(mc.getMoves(1000)) {
			// Count the occurrences of OPENING moves
//			mc.countMoves(1, 2);
//			Move open1 = mc.getMostMoved(1);
//			File open1Trial = mc.findTrial(open1, true);
//			makeImage(open1Trial, mc.getFoundMoveNum(), fileCount++, "opening");

			// Count the occurrences of CLOSING moves
			mc.countMoves(1, -2);
			final Move close1 = mc.getMostMoved(1);
			final File close1Trial = mc.findTrial(manager, close1, false);
			makeImage(app, close1Trial, mc.getFoundMoveNum(), fileCount++, "closing");

			// Count the occurrences of moves
			mc.countMoves(1, 2);
			mc.countMoves(2, 0);

			/** Generate image for each direction of moving **/
			final Map<String, Move> moveP1 = mc.getMoveType(1, "Move", -1);
			final int i = 0;
			for(final String s : moveP1.keySet()) {
				System.out.println(s + " -> " + moveP1.get(s));
				trialFile = mc.findTrial(manager, moveP1.get(s), false);
				makeImage(app, trialFile, mc.getFoundMoveNum(), fileCount++, "move");
			}

			mc.countMoves(1,  0);

			/** Generate image for each direction of capturing (stomping) **/
			final Map<String, Move> stompP1 = mc.getMoveType(1, "Stomp", -1);
			for(final String s : stompP1.keySet()) {
				System.out.println(s + " -> " + stompP1.get(s));
				trialFile = mc.findTrial(manager, stompP1.get(s), false);
				makeImage(app, trialFile, mc.getFoundMoveNum(), fileCount++, "capture");
			}
		}

		System.out.println("Finished!");
	}

	public static void makeImage(final PlayerApp app, final File tf, final int fmn, final int count, final String suffix) 
	{
		// So now we have trial files for a specific move + player (+ piece)
		// Load a trial to create an image
		wait(1000);
		TrialLoading.loadTrial(app, tf, false);

		// Go back until the move happened and take an image
		wait(1000);
		createMoveImage(app, fmn, count, suffix);
	}

	public enum LudGame{

		Chess("board/war/chess/Chess.lud"),
		Breakthrough("board/race/reach/Breakthrough.lud"),
		Amazons("board/space/blocking/Amazons.lud"),
		TicTacToe("board/space/line/Tic-Tac-Toe.lud");

		public final String path;

		private LudGame(final String path) {
			this.path = path;
		}

		@Override
		public String toString() {
			return path;
		}
	}

}
