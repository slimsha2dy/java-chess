# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## 기능 목록

### 입력

- [x] 게임 시작 또는 종료 명령을 입력받는다(start, end)
- [x] 체스 말의 이동 정보(move source, target)를 입력받는다.
    - [x] 체스판의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8임을 검증한다.

### 출력

- [x] 게임 안내 문구를 출력한다.
- [x] 체스판을 현 상황을 출력한다.
    - [x] 검은색 말은 대문자, 흰색 말은 소문자로 구분한다.

### ChessGame

### Board

- [x] 초기화 기능
    - [x] 체스말 놓기
- [x] 말을 움직이는 기능
    - [x] 움직이려는 자리에 말이 있는지 확인
    - [x] 이동경로의 방해물을 확인
    - [x] 정상적인 이동경로일 경우 말 이동
- [x] 정렬된 보드를 반환

### Piece

- [X] 말은 Pawn, Bishop, Knight, Rook, Queen, King 으로 구성
- [X] 말은 흑 또는 백
- [X] 상대위치와 타겟을 입력받아서 올바르게 움직일 수 있는지 확인
    - [X] 자신과 타겟이 같은 팀일경우 예외처리
    - [X] 올바르지 않은 방향으로 이동할 경우 예외처리

### ObstacleStrategy

- [x] 이동 경로에 장애물이 있는지 확인이 필요한 위치들의 리스트를 반환

### Movement

- [X] 상대 위치까지의 이동이 가능한지 판별

### RelativePosition

- [x] 상대 위치를 단위 상대 위치로 변환하는 기능

### Direction

- [x] 말의 최소 이동 단위 제공

### Position

- [x] 입력된 위치가 유효한지 검증
