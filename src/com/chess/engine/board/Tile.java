package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import static com.chess.engine.board.BoardUtils.NUM_TILES;

public abstract class Tile {
    protected final int tileCoordinates;
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTile();

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTile() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }
    public static Tile createTile(final int tileCoordinate, final Piece piece){
        if(piece != null){
            return new OccupiedTile(tileCoordinate, piece);
        }
        else{
            return EMPTY_TILES_CACHE.get(tileCoordinate);
        }
    }

    private Tile(final int tileCoordinates){
        this.tileCoordinates = tileCoordinates;
    }

    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile{
        private EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public String toString(){
            return "-";
        }
        @Override
        public boolean isOccupied(){
            return false;
        }
        @Override
        public Piece getPiece(){
            return null;
        }
    }
    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile;
        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }
        @Override
        public String toString(){
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
        @Override
        public boolean isOccupied(){
            return true;
        }
        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
    }
}
