package metrics.quality;

import org.apache.commons.rng.RandomProviderState;

import game.Game;
import metrics.Metric;
import metrics.Utils;
import other.context.Context;
import other.trial.Trial;

/**
 * Metric that measures maximum number of moves per turn
 * 
 * @author matthew.stephenson
 */
public class BranchingFactorMax extends Metric
{

	//-------------------------------------------------------------------------

	/**
	 * Constructor
	 */
	public BranchingFactorMax()
	{
		super
		(
			"Branching Factor Maximum", 
			"Maximum branching factor over all trials.", 
			"Core Ludii metric.", 
			MetricType.OUTCOMES,
			0.0, 
			-1,
			0.0,
			null
		);
	}
	
	//-------------------------------------------------------------------------
	
	@Override
	public double apply
	(
			final Game game,
			final String args, 
			final Trial[] trials,
			final RandomProviderState[] randomProviderStates
	)
	{
		if (trials.length == 0)
			return 0;
		
		double avgBranchingFactor = 0;
		for (int trialIndex = 0; trialIndex < trials.length; trialIndex++)
		{
			// Get trial and RNG information
			final Trial trial = trials[trialIndex];
			final RandomProviderState rngState = randomProviderStates[trialIndex];
			
			// Setup a new instance of the game
			final Context context = Utils.setupNewContext(game, rngState);
			
			// Record the number of possible options for each move.
			double maxLegalMovesSizes = context.game().moves(context).moves().size();
			
			for (int i = trial.numInitialPlacementMoves(); i < trial.numMoves()-1; i++)
			{
				context.game().apply(context, trial.getMove(i));
				maxLegalMovesSizes = Math.max(maxLegalMovesSizes, context.game().moves(context).moves().size());
			}
			
			avgBranchingFactor += maxLegalMovesSizes;
		}

		return avgBranchingFactor / trials.length;
	}

	//-------------------------------------------------------------------------

}
