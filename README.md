# 블랙잭
## 기능 요구 사항
- 플레이어는 게임 시작 시 배팅 금액을 정한다.
- 숫자 계산은 카드 숫자를 기본으로 한다.
  - ACE는 1 또는 11로 계산할 수 있다.
  - King, Queen, Jack은 각각 10으로 계산한다.
    
- 플레이어는 두 장의 카드를 시작 시 지급받는다.
- 카드 숫자의 합이 21을 넘지 않는 경우, 카드를 추가로 뽑을 수 있다.
- 카드 숫자의 합이 21을 넘으면 배팅 금액을 모두 잃는다.
- 첫 두 장의 합이 21일 경우 블랙잭이 되고, 배팅 금액의 1.5배를 딜러에게 받는다.
  - 딜러 또한 블랙잭인 경우, 배팅 금액을 돌려 받는다.
- 딜러는 첫 2장의 합이 16 이하이면 반드시 1장의 카드를 추가로 받아야 한다. 17 이상이면 추가로 받을 수 없다.
  - 딜러가 21을 초과하면 해당 시점에 남아있던 플레이어들은 패와 관계없이 승리한다.
    
## 테스트 사항
### InputView 로직
- 게임에 참여할 사람의 이름을 입력 받는다.
- 입력받은 이름의 순서에 따라 배팅 금액을 입력받는다.
- 게임을 시작한다는 문구에 따라, 딜러를 포함한 참가자들에게 카드를 나눈다.
- 참가자들에게 카드를 더 받는 지 묻는다.
- 카드를 더 받겠다고 밝힌 참가자들에게 카드를 준다.
  
### OutputView 로직
- 카드 분배가 모두 끝나면 각자의 카드와 합을 출력한다.
- 최종 수익을 계산해 분배한 결과를 출력한다.

### 서비스 로직
- 두 장의 카드가 중복되지 않고, 랜덤하게 주어진다. => 어떻게 랜덤을 없애지?
- 두 장의 카드가 주어졌을 때, 참가자는 합을 계산한다.
  - 두 장의 카드 합이 21이면 블랙잭을 표시한다. => 어떻게?
  - 세 장의 카드 합이 21을 넘으면 예외를 발생시킨다.
  - 두 장의 카드 중 1장이 ACE일 때, 21이 넘지 않는 최대값을 계산한다.
- 카드가 추가될 때마다 합을 계산한다.
- 결과에 따라 수익을 계산한다.

## 서비스 설계
- 카드 종료(하트, 클로버, 스페이드, 다이아)와 카드 숫자(A,2,3,4,5,6,7,8,9,10,J,Q,K)를 나눈다.
- 가능한 종류와 카드 숫자를 조합해 카드 덱을 만든다.
- 카드 덱을 셔플한 결과를 리스트에 저장한다.
- 해당 카드 덱에서 카드를 뽑을 때마다 사용된 카드를 관리해 중복을 막는다.
- 참가자들의 이름, 배팅금액, 받은 카드, 최종 수익 등을 한 곳에서 관리한다.