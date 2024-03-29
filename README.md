# 체스 게임

## 요구사항 정리

### 체스 판

1. 체스 판의 크기는 8 * 8 의 정사각형이다.
    - 체스 판의 가로 위치는 왼쪽 부터 a ~ h 로 표현한다.
    - 체스 판의 세로 위치는 아래 부터 1 ~ 8 로 표현한다.

### 체스 말

1. 체스 말에는 폰, 나이트, 비숍, 룩, 퀸, 킹 총 6개의 종류가 있다.
2. 체스 말마다 이동 규칙이 다르다.
    - 폰
        - 한 번도 이동한 적 없는 경우 상대 진영 방향으로 한 칸 혹은 두 칸 직진할 수 있다.
        - 이동한 적 있는 경우 상대 진영 방향으로 한 칸 직진할 수 있다.
        - 상대 진영 방향 대각선 한칸 위치에 상대 진영의 말이 있는 경우, 그 위치로 이동할 수 있다.
    - 나이트
        - 자신의 위치에서 가로 ± 2, 세로 ± 1 혹은 가로 ± 1, 세로 ± 2 한 위치로 이동할 수 있다.
    - 비숍
        - 대각선 방향으로 몇 칸이든 움직일 수 있다.
        - 이동하고자 하는 위치와, 현재 위치 사이에 다른 말이 있는 경우 그 위치로 이동할 수 없다.
    - 룩
        - 가로 혹은 세로 방향으로 몇 칸이든 움직일 수 있다.
        - 이동하고자 하는 위치와, 현재 위치 사이에 다른 말이 있는 경우 그 위치로 이동할 수 없다.
    - 퀸
        - 가로 혹은 세로 혹은 대각선 방향으로 몇 칸이든 움직일 수 있다.
        - 이동하고자 하는 위치와, 현재 위치 사이에 다른 말이 있는 경우 그 위치로 이동할 수 없다.
    - 킹
        - 가로 혹은 세로 혹은 대각선 방향으로 한 칸 움직일 수 있다.
3. 맵 밖으로는 움직일 수 없다.
4. 체스 말은 소속된 진영이 하나 있다.
    - 한번 체스 말의 진영이 정해지면 변경되지 않는다.
5. 체스 말이 이동할 수 있는 위치에 상대 진영의 말이 있는 경우, 그 말은 체스 게임에서 제거된다.
    - 단, 폰은 이동할 수 있는 위치에 상대 진영의 말이 있는 경우, 그 위치로 이동할 수 없다.
6. 체스 말이 이동할 수 있는 위치에 같은 진영의 말이 있는 경우, 그 위치로 이동할 수 없다.

## 기능 목록

- [x] 체스 말
    - [x] 체스 말 이동
        - [x] 이동 위치에 아무 말도 없는 경우 이동할 수 있다
        - [x] 이동 위치에 같은 진영 말이 있는 경우 이동할 수 없다
        - [x] 이동 경로에 다른 말이 있는 경우 이동할 수 없다
        - [x] 이동 위치에 상대 진영 말이 있는 경우 이동할 수 있다
        - [x] 특별한 이동 방법이 있는 경우 (폰)
- [x] 체스 보드
    - [x] 올바른 턴인지 확인한다
    - [x] 잡힌 말을 제거한다
    - [x] 한 쪽의 King이 잡히면 잡힌 쪽의 패배로 게임이 종료된다
    - [x] 현재 각 팀의 점수를 계산한다
- [x] 사용자 입력 기능
    - [x] 이동 위치 명령어 입력 기능
    - [x] 게임 시작 명령어 입력 기능
    - [x] 게임 종료 명령어 입력 기능
    - [x] 게임 점수 명령어 입력 기능
- [x] 출력 기능
    - [x] 체스 판 출력 기능
    - [x] 안내 문구 출력 기능
    - [x] 게임 점수 출력 기능
