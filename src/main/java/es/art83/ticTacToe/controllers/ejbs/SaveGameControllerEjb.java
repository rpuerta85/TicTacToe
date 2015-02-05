package es.art83.ticTacToe.controllers.ejbs;

import es.art83.ticTacToe.controllers.SaveGameController;
import es.art83.ticTacToe.models.daos.DaoFactory;
import es.art83.ticTacToe.models.entities.GameEntity;
import es.art83.ticTacToe.models.utils.StateModel;

public class SaveGameControllerEjb extends ControllerEjb implements SaveGameController {

    public SaveGameControllerEjb(Session session) {
        super(session);
    }

    @Override
    public void saveGame() {
        GameEntity game = this.getSession().getGame();
        DaoFactory.getFactory().getGameDao().update(game);
        this.getSession().setSavedGame(true);
        this.changeState();
    }

    @Override
    public void saveGame(String gameName) {
        GameEntity game = this.getSession().getGame();
        game.setName(gameName);
        DaoFactory.getFactory().getGameDao().create(game);
        this.getSession().setSavedGame(true);
        this.changeState();
    }

    @Override
    public void overWriteGame(String gameName) {
        GameEntity game = DaoFactory.getFactory().getGameDao()
                .findPlayerGame(this.getSession().getPlayer(), gameName);
        DaoFactory.getFactory().getGameDao().delete(game);
        this.saveGame(gameName);
        this.changeState();
    }

    @Override
    protected void changeState() {
        assert this.getSession().getState() == StateModel.CLOSED_GAME
                || this.getSession().getState() == StateModel.OPENED_GAME;
    }

}