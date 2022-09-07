package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    public static final Move NULL_MOVE = new NullMove();

    private Move(Board board, Piece movedPiece, int destinationCoordinates) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinates;
    }
    public int getCurrentCoordinate(){
        return this.movedPiece.getPiecePosition();
    }
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.movedPiece.getPiecePosition();
        result = prime * result + this.movedPiece.hashCode();
        return result;
    }
    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Move)){
            return false;
        }
        final Move otherMove = (Move) other;
        return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
    public Piece getMovedPiece(){
        return this.movedPiece;
    }
    public boolean isAttack(){
        return false;
    }
    public boolean isCastlingMove(){
        return false;
    }
    public Piece getAttackedPiece(){
        return null;
    }

    public Board execute(){
        final Board.Builder builder = new Board.Builder();
        for(final Piece piece : this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    public static final class MajorMove extends Move {
        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinates) {
            super(board, movedPiece, destinationCoordinates);
        }
    }
    public static class AttackMove extends Move {
        final Piece attackedPiece;
        public AttackMove(final Board board, final Piece movedPiece, final int destinationCoordinates, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinates);
            this.attackedPiece = attackedPiece;
        }
        @Override
        public Board execute(){
            return null;
        }
        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }
        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }
            if(!(other instanceof AttackMove)){
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }
        @Override
        public boolean isAttack(){
            return true;
        }
        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }
    }

    public static final class PawnMove extends Move {
        public PawnMove(final Board board, final Piece movedPiece, final int destinationCoordinates) {
            super(board, movedPiece, destinationCoordinates);
        }
        @Override
        public Board execute(){
            return null;
        }
    }
    public static class PawnAttackMove extends AttackMove {
        public PawnAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinates, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinates, attackedPiece);
        }
        @Override
        public Board execute(){
            return null;
        }
    }
    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinates, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinates, attackedPiece);
        }
        @Override
        public Board execute(){
            return null;
        }
    }
    public static final class PawnJumpMove extends Move {
        public PawnJumpMove(final Board board, final Piece movedPiece, final int destinationCoordinates) {
            super(board, movedPiece, destinationCoordinates);
        }
        @Override
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                builder.setPiece(piece);
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }
    static abstract class CastleMove extends Move {
        public CastleMove(final Board board, final Piece movedPiece, final int destinationCoordinates) {
            super(board, movedPiece, destinationCoordinates);
        }
    }
    public static final class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinates) {
            super(board, movedPiece, destinationCoordinates);
        }
        @Override
        public Board execute(){
            return null;
        }
    }
    public static final class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinates) {
            super(board, movedPiece, destinationCoordinates);
        }
        @Override
        public Board execute(){
            return null;
        }
    }
    public static final class NullMove extends Move {
        public NullMove() {
            super(null,  null, -1);
        }
        @Override
        public Board execute(){
            throw new RuntimeException("Cannot execute the null move!");
        }
    }
    public static class MoveFactory{
        private MoveFactory(){
            throw new RuntimeException("Not instantiable!");
        }
        public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate){
            for(final Move move : board.getAllLegalMoves()){
                if(move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
