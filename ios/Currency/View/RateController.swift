//
//  RateController.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class RateController: BaseTableViewController<RateModel, RateViewModel>, RateView, RateChangeDelegate {
	
	var dataSet: ObservableList<RateEntity>!
	var dataSource: RateDataSource!
	
	var rateManager: RateManager!
	
  override func setUp() {
		tableView.register(SimpleRateCell.self)
		// seperator of none
    tableView.separatorStyle = .none
		tableView.rowHeight = UITableView.automaticDimension
		
		// register data source
		tableView.dataSource = dataSource
  }

  override func attach() {
		dataSet.register(tableView)
		rateManager.addRateChangeDelegate(self)
		// we gotto register before ui
		super.attach()
		
		let interval = DispatchTimeInterval.seconds(1)
	
		disposeBag += Observable<Int>.interval(interval, scheduler: MainScheduler.asyncInstance)
			.map { [unowned self] _ in LoadRateEvent(base: self.rateManager.rate.base) }
			.subscribe(onNext: accept(_ :))
	}

  override func render(model: RateModel) {
    switch model.state {
      case .idle: break;
      case .failure(_): break;
      case .operation(_, _):
				render(model.data)
				break;
    }
  }
	
	override func detach() {
		dataSet.unregister(tableView)
		rateManager.removeRateChangeDelegate(self)
		super.detach()
	}
	
	func rateChange(newValue: RateEntity) {
		if isEditing {
			setEditing(false, animated: true)
		}
		if newValue != .empty {
			let path = IndexPath(row: 0, section: 0)
			self.tableView.scrollToRow(at: path, at: .top, animated: false)
			
			let position = dataSet.indexOf(newValue)
			if position != -1 && position != 0 {
				let value = dataSet.get(position)
				value.amount = newValue.amount
				
				dataSet.remove(at: position)
				dataSet.insert(value, at: 0)
			}
		}
	}
	
	private func render(_ data: Dictionary<String, Double>) {
		if !data.isEmpty {
			let currentRate = rateManager.rate
			var newData = data
			newData[currentRate.base] = 1.0
			
			rateManager.rates = newData
			if dataSet.isEmpty {
				let items = data.keys.map { base in RateEntity(base, 0.0) }
				dataSet.append(rateManager.rate)
				dataSet.append(items)
			}
		}
	}
}
