package com.example.game;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.example.computerplayer.ComputerPlayer;
import com.example.screen.Screen;

public class GameView extends View {
	/**
	 * 屏幕的宽
	 */
	private static int screenWidth = Screen.screenWidth;
	/**
	 * 屏幕的高
	 */
	private static int screenHeight = Screen.screenHeight;
	/**
	 * 棋盘的行数
	 */
	public static final int ROWS = 15;
	/**
	 * 棋盘的列数
	 */
	public static final int COLS = 15;
	/**
	 * 棋子在棋盘的分布0为无子，1为白子,2为黑子
	 */
	private ChessType[][] chessMap = new ChessType[ROWS][COLS];

	/*
	 * private static float PADDING = ((screenWidth) / (COLS - 1)) / 2f; private
	 * static float PADDING_LEFT = ((screenWidth) / (COLS - 1)) / 2.0f; private
	 * static float PADDING_TOP = ((screenHeight) / (ROWS -1)) / 2.0f; private
	 * static float ROW_MARGIN = (float) ((screenHeight - PADDING * 2.0) / (ROWS
	 * - 1.0)); private static float COL_MARGIN = (float) ((screenWidth -
	 * PADDING * 2.0) / (COLS - 1.0)); private static float MARGIN;
	 */

	public static float GRID_WIDTH; // 棋盘格的宽度
	public static float GRID_HEIGHT;// 棋盘格的高度
	public static float CHESS_DIAMETER; // 棋的直径
	public static float mStartX;// 棋盘定位的左上角X
	public static float mStartY;// 棋盘定位的左上角Y

	// 判断游戏是否结束
	private boolean gameOver = false;
	// 主activity
	private Context context = null;
	// 电脑的棋子颜色
	private ChessType computerType = ChessType.BLACK;
	// 玩家的棋子颜色
	private ChessType playerType = ChessType.WHITE;
	private ComputerPlayer computerPlayer = new ComputerPlayer(chessMap,
			computerType, playerType);

	public GameView(Context context) {
		super(context);
		this.context = context;
		// PADDING_LEFT = ((screenWidth) / (COLS - 1)) / 2;
		// PADDING_TOP = ((screenHeight) / (ROWS - 1)) / 2;
		// PADDING = PADDING_LEFT < PADDING_TOP ? PADDING_LEFT : PADDING_TOP;
		// ROW_MARGIN = ((screenHeight - PADDING * 2)) / (ROWS - 1);
		// COL_MARGIN = ((screenWidth - PADDING * 2)) / (COLS - 1);
		// MARGIN = ROW_MARGIN < COL_MARGIN ? ROW_MARGIN : COL_MARGIN;
		// PADDING_LEFT = (screenWidth - (COLS - 1) * MARGIN) / 2;
		// PADDING_TOP = (screenHeight - (ROWS - 2) * MARGIN) / 2;
		// 对棋子进行初始化
		GRID_WIDTH = screenWidth / 17.0f;
		GRID_HEIGHT = GRID_WIDTH * 24 / 25.0f;
		CHESS_DIAMETER = GRID_WIDTH * 20 / 25.0f;

		initChess();
		// System.out.println(PADDING_LEFT + " " + PADDING_TOP);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mStartX = w / 2 - (ROWS - 1) * GRID_WIDTH / 2;
		mStartY = h / 2 - (COLS - 1) * GRID_HEIGHT / 2;
	}

	/**
	 * 对棋子进行初始化
	 */
	public void initChess() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				chessMap[i][j] = ChessType.NONE;
			}
		}
		invalidate();
	}

	/**
	 * 游戏重新开始
	 */
	public void reStart() {
		initChess();
		gameOver = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.argb(255, 255, 236, 139));

		Paint paintRect = new Paint();
		paintRect.setColor(Color.GRAY);
		paintRect.setStrokeWidth(2);
		paintRect.setStyle(Style.STROKE);

		for (int i = 0; i < ROWS - 1; i++) {
			for (int j = 0; j < COLS - 1; j++) {

				float mLeft = (i * GRID_WIDTH + mStartX);
				float mTop = (j * GRID_HEIGHT + mStartY);
				float mRright = (mLeft + GRID_WIDTH);
				float mBottom = (mTop + GRID_HEIGHT);

				canvas.drawRect(mLeft, mTop, mRright, mBottom, paintRect);
				if (i == 3 && j == 3) {
					Paint paintCircle = new Paint();
					canvas.drawCircle(mStartX + i * GRID_WIDTH, mStartY + j
							* GRID_HEIGHT, CHESS_DIAMETER / 6, paintCircle);
				}
				if (i == 3 && j == 11) {
					Paint paintCircle = new Paint();
					canvas.drawCircle(mStartX + i * GRID_WIDTH, mStartY + j
							* GRID_HEIGHT, CHESS_DIAMETER / 6, paintCircle);
				}
				if (i == 11 && j == 3) {
					Paint paintCircle = new Paint();
					canvas.drawCircle(mStartX + i * GRID_WIDTH, mStartY + j
							* GRID_HEIGHT, CHESS_DIAMETER / 6, paintCircle);
				}
				if (i == 11 && j == 11) {
					Paint paintCircle = new Paint();
					canvas.drawCircle(mStartX + i * GRID_WIDTH, mStartY + j
							* GRID_HEIGHT, CHESS_DIAMETER / 6, paintCircle);
				}
				if (i == 7 && j == 7) {
					Paint paintCircle = new Paint();
					canvas.drawCircle(mStartX + i * GRID_WIDTH, mStartY + j
							* GRID_HEIGHT, CHESS_DIAMETER / 6, paintCircle);
				}
			}

		}

		// 画棋盘的外边框
		paintRect.setStrokeWidth(2);
		paintRect.setColor(Color.GRAY);
		canvas.drawRect(mStartX - CHESS_DIAMETER, mStartY - CHESS_DIAMETER,
				mStartX + CHESS_DIAMETER + GRID_WIDTH * (COLS - 1), mStartY
						+ CHESS_DIAMETER + GRID_HEIGHT * (ROWS - 1), paintRect);
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				// System.out.print(chessMap[r][c] + " ");
				if (chessMap[r][c] == ChessType.NONE)
					continue;
				if (chessMap[r][c] == ChessType.BLACK) {
					Paint paintCircle = new Paint();
					paintCircle.setColor(Color.BLACK);
					canvas.drawCircle(mStartX + r * GRID_WIDTH, mStartY + c
							* GRID_HEIGHT, CHESS_DIAMETER / 2, paintCircle);
				} else if (chessMap[r][c] == ChessType.WHITE) {
					Paint paintCircle = new Paint();
					paintCircle.setColor(Color.WHITE);
					canvas.drawCircle(mStartX + r * GRID_WIDTH, mStartY + c
							* GRID_HEIGHT, CHESS_DIAMETER / 2, paintCircle);
				}
			}
		}
	}

	/**
	 * 判断是否胜利
	 */
	public boolean hasWin(int r/*行*/, int c/*列*/) {
		ChessType chessType = chessMap[r][c];
		System.out.println(chessType);
		int count = 1;

		// 纵向搜索
		//向下搜索
		for (int i = r + 1; i < r + 5; i++) {
			if (i >= GameView.ROWS)
				break;
			if (chessMap[i][c] == chessType) {
				count++;
			} else
				break;
		}
		//向上搜索
		for (int i = r - 1; i > r - 5; i--) {
			if (i < 0)
				break;
			if (chessMap[i][c] == chessType)
				count++;
			else
				break;
		}
		if (count >= 5)
			return true;

		// 横向搜索
		count = 1;//计数重置
		for (int i = c + 1; i < c + 5; i++) {
			if (i >= GameView.COLS)
				break;
			if (chessMap[r][i] == chessType)
				count++;
			else
				break;
		}
		for (int i = c - 1; i > c - 5; i--) {
			if (i < 0)
				break;
			if (chessMap[r][i] == chessType)
				count++;
			else
				break;
		}
		if (count >= 5)
			return true;
		// 斜向"\"
		count = 1;
		for (int i = r + 1, j = c + 1; i < r + 5; i++, j++) {
			if (i >= GameView.ROWS || j >= GameView.COLS) {
				break;
			}
			if (chessMap[i][j] == chessType)
				count++;
			else
				break;
		}
		for (int i = r - 1, j = c - 1; i > r - 5; i--, j--) {
			if (i < 0 || j < 0)
				break;
			if (chessMap[i][j] == chessType)
				count++;
			else
				break;
		}
		if (count >= 5)
			return true;
		// 斜向"/"
		count = 1;
		for (int i = r + 1, j = c - 1; i < r + 5; i++, j--) {
			if (i >= GameView.ROWS || j < 0)
				break;
			if (chessMap[i][j] == chessType)
				count++;
			else
				break;
		}
		for (int i = r - 1, j = c + 1; i > r - 5; i--, j++) {
			if (i < 0 || j >= GameView.COLS)
				break;
			if (chessMap[i][j] == chessType)
				count++;
			else
				break;
		}
		// System.out.println(count +" " +"4");
		if (count >= 5)
			return true;
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int r;
		int c;
		float r0 = GRID_WIDTH - (event.getX() - mStartX) % GRID_WIDTH;// 鼠标点击处的横坐标到交叉点的偏移量
		float c0 = GRID_HEIGHT - (event.getY() - mStartY) % GRID_HEIGHT;// 鼠标点击处的纵坐标到交叉点的偏移量
		/**
		 * 求出最靠近鼠标点击处的交叉点
		 */
		if (r0 < GRID_WIDTH / 2) {
			r = (int) ((event.getX() - mStartX) / GRID_WIDTH) + 1;
		} else {
			r = (int) ((event.getX() - mStartX) / GRID_WIDTH);
		}
		if (c0 < GRID_HEIGHT / 2) {
			c = (int) ((event.getY() - mStartY) / GRID_HEIGHT) + 1;
		} else {
			c = (int) ((event.getY() - mStartY) / GRID_HEIGHT);
		}
		// System.out.println(r + " " + c);
		if (!(r >= 0 && r < ROWS && c >= 0 && c < COLS)) {
			return false;
		}
		if (!gameOver) {
			if (chessMap[r][c] == ChessType.NONE) {
				chessMap[r][c] = this.playerType;
				if (this.hasWin(r, c)) {
					// 玩家胜利
					this.gameOver = true;
					showMessage("玩家胜利");
				}
				Point p = computerPlayer.start();
				chessMap[p.x][p.y] = this.computerType;
				if (this.hasWin(p.x, p.y)) {
					// 电脑胜利
					this.gameOver = true;
					showMessage("电脑胜利");
				}
			}
		} else {
			showMessage("游戏已结束,是否重新开始?");

		}
		this.invalidate();
		return super.onTouchEvent(event);
	}

	// 消息显示
	private void showMessage(String message) {
		AlertDialog alert = new AlertDialog.Builder(context).create();
		alert.setTitle("提示");
		alert.setMessage(message);
		alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						reStart();
					}
				});
		alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		alert.show();
	}
}
