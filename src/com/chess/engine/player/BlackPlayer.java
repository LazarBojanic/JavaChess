package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player{
    public BlackPlayer(final Board board, final Collection<Move> blackStandardLegalMoves,
                       final Collection<Move> whiteStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }
    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            if(!this.board.getTile(5).isOccupied() && !this.board.getTile(6).isOccupied()){
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 6,
                                (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            if(!this.board.getTile(1).isOccupied() && !this.board.getTile(2).isOccupied()
                    && !this.board.getTile(3).isOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()){
                    kingCastles.add(new Move.KingSideCastleMove(this.board, this.playerKing, 2,
                            (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
