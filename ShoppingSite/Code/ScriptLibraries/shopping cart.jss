/*
 * ショッピングカートの内容をクリア
 */
function clearCart() {
	if (sessionScope.cart) {
		sessionScope.cart = null;
	}
}

/*
 * ショッピングカートのオーダーを更新
 * _noteID		カタログ文書の Note ID
 * _prodId		商品番号
 * _prodName	商品名
 * _prodPrice	価格
 * _orderCount	購入点数
 */
function updateOrder(_noteId, _prodId, _prodName, _prodPrice, _orderCount) {

	/*
	 * セッションス範囲のコープ変数からカートデータを取得、なければ生成
	 */
	var list = new Array();
	if (sessionScope.cart) {
		list = sessionScope.cart;
	} else {
		list = new Array();
	}
	
	/*
	 * アップデートする商品があるかを確認し、なければ追加、あれば更新
	 * 購入点数が 0 なら、オーダーから削除
	 */
	var index = findOrder(list, _prodId);
	if ((-1 == index) && (0 != _orderCount)) {
		list = addOrder(list, _noteId, _prodId, _prodName, _prodPrice, _orderCount);
	} else {
		if (0 == _orderCount) {
			list = removeOrder(list, index);
		} else {
			list = replaceOrder(list, index, _noteId, _prodId, _prodName, _prodPrice, _orderCount)
		}
	}

	/*
	 * 商品がなければカート自体を null にする
	 */
	if (0 == list.length) {
		list = null;
	}
	
	/*
	 * 更新されたカートのデータをスコープ変数に保存
	 */
	sessionScope.cart = list;
}

/*
 * ショッピングカートからオーダーを参照
 * _prodId		商品番号
 */
function getOrder(_prodId) {
	
	/*
	 * セッションスコープからカートデータを取得、なければゼロを返す
	 */
	var list = new Array();
	if (sessionScope.cart) {
		list = sessionScope.cart;
		var index = findOrder(list, _prodId);
		if (-1 < index) {
			return list[index].orderCount;
		}
	}
	
	return 0;
}

/*
 * ショッピングカートにある商品数の取得
 */
function getProductCount() {
	if (sessionScope.cart) {
		var list = sessionScope.cart;
		return list.length;
	} else {
		return 0;
	}
}

/*
 * ショッピングカート内の商品の合計金額を取得
 */
function getTotalAmount() {
	if (sessionScope.cart) {
		var list = sessionScope.cart;
		
		if (list.length > 0) {
			var amount = 0;
			for(var i=0; i<list.length; i++) {
				var productPrice = list[i].productPrice; // 商品価格
				var orderCount = list[i].orderCount; // 購入点数
				amount += productPrice * orderCount;
			}
			return amount;
		} else {
			return 0;
		}
	} else {
		return 0;
	}
}

/*
 * ショッピングカートから一覧を取得
 */
function getAllOrder() {
	return sessionScope.cart;
}

/*
 * プライベート関数：ショッピングカートにオーダーを追加
 */
function addOrder(_list, _noteId, _prodId, _prodName, _prodPrice, _orderCount) {
	var entry = new Object();
	entry.noteId = _noteId;
	entry.productId = _prodId;
	entry.productName = _prodName;
	entry.productPrice = _prodPrice;
	entry.orderCount = _orderCount;

	_list.push(entry);

	return _list;
}

/*
 * プライベート関数：ショッピングカートにオーダーを変更
 */
function replaceOrder(_list, _index, _noteId, _prodId, _prodName, _prodPrice, _orderCount) {
	var entry = _list[_index];
	entry.noteId = _noteId;
	entry.productId = _prodId;
	entry.productName = _prodName;
	entry.productPrice = _prodPrice;
	entry.orderCount = _orderCount;

	return _list;
}

/*
 * プライベート関数：ショッピングカートからオーダーを検索
 */
function findOrder(_list, _prodId) {
	for(var i=0; i<_list.length; i++) {
		var productId = _list[i].productId; // 商品コード
		if (productId == _prodId) {
			return i;
		}
	}

	return -1;
}

/*
 * プライベート関数：ショッピングカートからオーダーを削除
 */
function removeOrder(_list, _index) {
	var newList = new Array();
	
	for(var i=0; i<_list.length; i++) {
		if (_index != i) {
			newList.push(_list[i]);
		}
	}

	return newList;
}

/*
 * デバッグ用関数：ショッピングカーとの内容をダンプ
 */
function dumpCart() {
	
	if (sessionScope.cart) {
		var list = sessionScope.cart;
		var message = "";
		for(var i=0; i<list.length; i++) {
			var noteId = list[i].noteId;				// 文書 ID
			var productId = list[i].productId;			// 商品コード
			var productName = list[i].productName;		// 商品名
			var productPrice = list[i].productPrice;	// 商品価格
			var orderCount = list[i].orderCount;		// 購入点数
			message += "[NoteID=";
			message += noteId;
			message += "]: 商品番号=";
			message += productId;
			message += ", ";
			message += productName;
			message += " (";
			message += productPrice;
			message += "円) を";
			message += orderCount;
			message += "個購入";
			message += "\n";
		}
		print(message);
	} else {
		print ("ショッピングカートが空です。");
	}
}