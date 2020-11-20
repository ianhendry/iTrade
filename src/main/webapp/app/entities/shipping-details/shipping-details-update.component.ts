import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IShippingDetails, ShippingDetails } from 'app/shared/model/shipping-details.model';
import { ShippingDetailsService } from './shipping-details.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-shipping-details-update',
  templateUrl: './shipping-details-update.component.html',
})
export class ShippingDetailsUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  dateAddedDp: any;
  dateInactiveDp: any;

  editForm = this.fb.group({
    id: [],
    userName: [],
    email: [],
    name: [],
    address1: [],
    address2: [],
    address3: [],
    address4: [],
    address5: [],
    dateAdded: [],
    dateInactive: [],
    userPicture: [],
    userPictureContentType: [],
    user: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected shippingDetailsService: ShippingDetailsService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shippingDetails }) => {
      this.updateForm(shippingDetails);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(shippingDetails: IShippingDetails): void {
    this.editForm.patchValue({
      id: shippingDetails.id,
      userName: shippingDetails.userName,
      email: shippingDetails.email,
      name: shippingDetails.name,
      address1: shippingDetails.address1,
      address2: shippingDetails.address2,
      address3: shippingDetails.address3,
      address4: shippingDetails.address4,
      address5: shippingDetails.address5,
      dateAdded: shippingDetails.dateAdded,
      dateInactive: shippingDetails.dateInactive,
      userPicture: shippingDetails.userPicture,
      userPictureContentType: shippingDetails.userPictureContentType,
      user: shippingDetails.user,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('iTradeApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shippingDetails = this.createFromForm();
    if (shippingDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.shippingDetailsService.update(shippingDetails));
    } else {
      this.subscribeToSaveResponse(this.shippingDetailsService.create(shippingDetails));
    }
  }

  private createFromForm(): IShippingDetails {
    return {
      ...new ShippingDetails(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      email: this.editForm.get(['email'])!.value,
      name: this.editForm.get(['name'])!.value,
      address1: this.editForm.get(['address1'])!.value,
      address2: this.editForm.get(['address2'])!.value,
      address3: this.editForm.get(['address3'])!.value,
      address4: this.editForm.get(['address4'])!.value,
      address5: this.editForm.get(['address5'])!.value,
      dateAdded: this.editForm.get(['dateAdded'])!.value,
      dateInactive: this.editForm.get(['dateInactive'])!.value,
      userPictureContentType: this.editForm.get(['userPictureContentType'])!.value,
      userPicture: this.editForm.get(['userPicture'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShippingDetails>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
